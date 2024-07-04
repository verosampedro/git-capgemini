package com.catalogo.domains.entities.models;


import com.catalogo.domains.entities.Language;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LanguageDTO {
	private int languageId;

	private String name;
	
	public static LanguageDTO from(Language source) {
		return new LanguageDTO(
				source.getLanguageId(),
				source.getName());
	}
	
	public static Language from(LanguageDTO source) {
		return new Language(
				source.getLanguageId(),
				source.getName());
	}
}
