package com.company.inventory.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponseRest extends ResponseRest {

	private ProductResponse product = new ProductResponse();

}
