package com.arilsongomes.workshop;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@Transactional
public class ItemControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ItemRespository itemRepository;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testReadRoot() throws Exception {
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
	}

	@Test
	public void testGetItemsEmpty() throws Exception {
		// Limpa o repositório para garantir que está vazio
		itemRepository.deleteAll();

		mockMvc.perform(get("/items"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	public void testCreateAndGetItem() throws Exception {
		// Limpa o repositório
		itemRepository.deleteAll();

		// Create an item
		Item testItem = new Item();
		testItem.setName("Test Item");
		testItem.setDescription("This is a test item");

		MvcResult createResult = mockMvc.perform(post("/items")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(testItem)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Test Item")))
				.andExpect(jsonPath("$.description", is("This is a test item")))
				.andExpect(jsonPath("$.id", notNullValue()))
				.andReturn();

		// Extrai o item criado
		String responseContent = createResult.getResponse().getContentAsString();
		Item createdItem = objectMapper.readValue(responseContent, Item.class);

		// Get all items
		mockMvc.perform(get("/items"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].id", is(createdItem.getId().toString())));

		// Get specific item
		mockMvc.perform(get("/items/" + createdItem.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(createdItem.getId().toString())))
				.andExpect(jsonPath("$.name", is("Test Item")))
				.andExpect(jsonPath("$.description", is("This is a test item")));
	}

	@Test
	public void testGetItemNotFound() throws Exception {
		UUID nonExistentId = UUID.randomUUID();

		mockMvc.perform(get("/items/" + nonExistentId))
				.andExpect(status().isNotFound());
	}
}