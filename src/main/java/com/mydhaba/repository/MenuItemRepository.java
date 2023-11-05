package com.mydhaba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mydhaba.model.Item;

public interface MenuItemRepository extends JpaRepository<Item, Integer> {

}
