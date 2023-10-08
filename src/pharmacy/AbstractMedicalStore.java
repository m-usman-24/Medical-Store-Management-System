package pharmacy;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public abstract class AbstractMedicalStore implements Serializable {
		
		private Map<String, Product> listOfProducts = new LinkedHashMap<>();
		private Map<String, Customer> listOfCustomers = new LinkedHashMap<>();
		
		//First String contains productId and the second String contains nameOfProduct
		public Map<String, String> getMatchingProductIds(String nameOfProduct) {
				
				Map<String, String> matchingProductIds = new LinkedHashMap<>();
				
				for (Map.Entry<String, Product> productEntry : listOfProducts.entrySet()) {
						
						if (productEntry.getValue().getNameOfProduct().contains(nameOfProduct)) {
								matchingProductIds.put(productEntry.getKey(), productEntry.getValue().getNameOfProduct() + " " +
									productEntry.getValue().getPotency() + productEntry.getValue().getUnitOfMeasure() + " \t " +
												"Stock Left: " + productEntry.getValue().getRemainingStock());
						}
				}
				return matchingProductIds;
		}
		
		public Map<String, String> getMatchingCustomerIds(String nameOfCustomer) {
				
				Map<String, String> matchingCustomerIds = new LinkedHashMap<>();
				
				for (Map.Entry<String, Customer> customerEntry : listOfCustomers.entrySet()) {
						
						if (customerEntry.getValue().getNameOfCustomer().contains(nameOfCustomer)) {
								matchingCustomerIds.put(customerEntry.getKey(), customerEntry.getValue().getNameOfCustomer());
						}
				}
				return matchingCustomerIds;
		}
}
