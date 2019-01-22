package com.common;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.Json;

/**
 * @Author xcd
 * @Aata 2019年1月10日
 * @Description
 */
public class ObjectConversion {
	public static void main(String[] args) {

//		ObjectNode node = Json.newObject();
//		node.put("id", "aa");
//		node.put("role", "aa");
//		System.out.println(node.toString());
		Map<String, String> map = new HashMap<String, String>();
		map.put("a", "aaa");
		map.put("b", "bbb");
		map.put("c", "ccc");
		map.forEach((k, v) -> System.out.println(k + ": " + v));
	}
}
