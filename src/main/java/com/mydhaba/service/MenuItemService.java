package com.mydhaba.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydhaba.CustomResponse;
import com.mydhaba.model.Item;
import com.mydhaba.repository.MenuItemRepository;

@Service
public class MenuItemService {

	@Autowired
	private MenuItemRepository menuItemRepository;
	
	public CustomResponse getItemById(int id) {
		try {
			Optional<Item> optionalMenuItem=menuItemRepository.findById(id);
			if(optionalMenuItem.isPresent()) {
				Item item=optionalMenuItem.get();
				return new CustomResponse("success", item);
			}
			return new CustomResponse("No item found for id "+id, null);
		} catch (Exception e) {
			return new CustomResponse("Something went wrong!", null);
		}
	}
	
	public CustomResponse getAllItems() {
		try {
			List<Item> list= (List<Item>) menuItemRepository.findAll();
			if(!list.isEmpty()) {
				return new CustomResponse("Success", list);
			}
			else {
				return new CustomResponse("No item found ", null);
			}
			
		} catch (Exception e) {
			return new CustomResponse("Something went wrong!", null);
		}
	}
	
	public CustomResponse addNewItem(Item item) {
		try {
			Item dbItem=menuItemRepository.save(item);
			return new CustomResponse("Item added successfully", dbItem);
			
		} catch (Exception e) {
			return new CustomResponse("Something went wrong!", null);
		}
	}
	
	public CustomResponse deleteItem(int id) {
		try {
			menuItemRepository.deleteById(id);
			return new CustomResponse("Deleted successfully!!", "id"+id);
		} catch (Exception e) {
			return new CustomResponse("Cannot delete this item", null);
		}
	}
	
	public CustomResponse updateItem(int id, Item item) {
		try {
			Optional<Item> optionalMenuItem=menuItemRepository.findById(id);
			if(optionalMenuItem.isPresent()) {
				Item dbItem=optionalMenuItem.get();
				dbItem.setItemName(item.getItemName());
				dbItem.setItemPrice(item.getItemPrice());
				dbItem=menuItemRepository.save(dbItem);
				return new CustomResponse("success", dbItem);
			}
			else {
				return new CustomResponse("No item found with id = " + id, null);
			}
		} catch (Exception e) {
			return new CustomResponse("Something went wrong!", null);
		}
	}
}
