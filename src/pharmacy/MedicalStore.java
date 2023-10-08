package pharmacy;

import lombok.EqualsAndHashCode;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
public class MedicalStore extends AbstractMedicalStore implements Dependable {
		
		private static final String FILE_SUCCESSFULLY_EXPORTED_POPUP = "\nFile Successfully Exported,\nBrowse Path: ";
		private static final String FILE_NOT_ACCESSIBLE_POPUP = "File is not Accessible, " +
						"could be in use by another Process, \nTry Exporting, after Closing the File.\n";
		
		long productNumber;
		long billNumber;
		long customerNumber;
		
//			                                             PRODUCT METHODS
		
		private String generateProductId() {
				productNumber++;
				return String.format("%s-P%d", LocalDate.now(), productNumber);
		}
		
		public void setProduct() {

				System.out.print("\nEnter Product Name: ");
				String nameOfProduct = inStr.nextLine().toUpperCase();

				System.out.print("Enter Potency of Product: ");
				int potency = in.nextInt();

				System.out.print("Enter Unit of Measure: ");
				String unitOfMeasure = inStr.nextLine().toLowerCase();

				System.out.print("Enter the batch Number: ");
				long batchNumber = in.nextLong();

				System.out.print("Enter Product Category: ");
				String category = inStr.nextLine();

				System.out.print("Enter Product Manufacturer: ");
				String manufacturer = inStr.nextLine();

				System.out.print("Enter Manufacture Date: ");
				String manufactureDate = inStr.nextLine();

				System.out.print("Enter Expiry Date: ");
				String expiryDate = inStr.nextLine();

				System.out.print("Enter Whole Sale Price: ");
				double wholeSalePrice = in.nextDouble();

				System.out.print("Enter Retail Price: ");
				double retailPrice = in.nextDouble();

				System.out.print("Enter the Stock: ");
				long stock = in.nextLong();

				String tempProductId = generateProductId();

		    getListOfProducts().put(tempProductId, new Product(tempProductId, nameOfProduct, potency, unitOfMeasure,
				    batchNumber, category, manufacturer, manufactureDate, expiryDate, wholeSalePrice, retailPrice, stock));
		}
		
		private String selectProductId() {
				
				String selectedProductId = null;
				
				System.out.print("\nEnter Name of the Product: ");
				String nameOfProductToSearch = inStr.nextLine().toUpperCase();
				
				if (nameOfProductToSearch.equalsIgnoreCase("done")) {
						return "done";
				}
				
				Map<String, String> matchedProductIds = getMatchingProductIds(nameOfProductToSearch);
				
				if (matchedProductIds.size() == 0) {
						
						System.out.println("\nProduct with this name don't Exist.");
						
				} else {
						
						System.out.println("\nCopy & Paste a single Product ID to select a Product Below;\n");
						
						for (Map.Entry<String, String> matchedProductEntry : matchedProductIds.entrySet()) {
								System.out.println(matchedProductEntry.getKey() + " : \t " + matchedProductEntry.getValue());
						}
						
						System.out.print("\nPaste Here: ");
						selectedProductId = inStr.nextLine();
				}
				
				return selectedProductId;
		}
		
		public void viewExpiredBatchDetails() {
				
				int  counter = 1;
				boolean isThereAnyExpiredProduct = false;
				
				for (Product productValue : getListOfProducts().values()) {
						
						if (productValue.getExpiredBatchDetail() != null) {
								System.out.println("\n" + counter++ + "\t" + productValue.getExpiredBatchDetail());
								
								if (!isThereAnyExpiredProduct) {
										isThereAnyExpiredProduct = true;
								}
						}
				}
				
				if (!isThereAnyExpiredProduct) {
						System.out.println("\nNo Expired Product Found!");
				}
		}
		
		public void searchASpecificProduct() {
				
				String selectedProductId = selectProductId();
				
				if (selectedProductId == null) {
						return;
				}
				
				System.out.println("\nSelected " + getListOfProducts().get(selectedProductId)
								.getSpecificProductDetails());
		}
		
		public void viewProductInventory() {
				
				if (getListOfProducts().isEmpty()) {
						System.out.println("\nNo Product Exists in the Product Inventory.");
						return;
				}
				
				System.out.println();
				
				int counter = 1;
				
				for (Product product : getListOfProducts().values()) {
						System.out.println(counter++ + ": " + product.getSpecificProductBriefDetails());
				}
		}
		
		public void productRestock() {
				
				String selectedProductId = selectProductId();
				
				if (selectedProductId == null) {
						return;
				}
				
				System.out.print("Enter re-stock quantity: ");
				long updatedStockQuantity = in.nextLong();
				
				System.out.print("Update batch number: ");
				long updatedBatchNumber = in.nextLong();
				
				System.out.print("Update Manufacture Date: ");
				String updatedManufactureDate = inStr.nextLine();
				
				System.out.print("Update Expiry Date: ");
				String updatedExpiryDate = inStr.nextLine();
				
				System.out.print("Is there a price change while re-stocking (Yes/No): ");
				String isThereAPriceChange = inStr.nextLine();
				
				double updatedWholesalePrice = 0;
				double updatedRetailPrice = 0;
				
				if (isThereAPriceChange.equalsIgnoreCase("yes")) {
						
						System.out.print("Update Wholesale Price: ");
						updatedWholesalePrice = in.nextDouble();
						
						System.out.print("Update Retail Price: ");
						updatedRetailPrice = in.nextDouble();
						
				} else if (!isThereAPriceChange.equalsIgnoreCase("no")){
						System.out.println("Wrong input, Restock Terminated, Try Again...");
						return;
				}
				
				Product selectedProduct = getListOfProducts().get(selectedProductId);
				
				selectedProduct.setRemainingStock(updatedStockQuantity);
				selectedProduct.setBatchNumber(updatedBatchNumber);
				selectedProduct.setManufactureDate(updatedManufactureDate);
				selectedProduct.setExpiryDate(updatedExpiryDate);
				
				if (isThereAPriceChange.equalsIgnoreCase("yes")) {
						
						selectedProduct.setWholeSalePrice(updatedWholesalePrice);
						selectedProduct.setRetailPrice(updatedRetailPrice);
				}
		}
		
		public void deleteProduct() {
				
				String selectedProductId = selectProductId();
				
				if (selectedProductId == null) {
						return;
				}
				
				getListOfProducts().remove(selectedProductId);
				
				if (getListOfProducts().get(selectedProductId) == null) {
						System.out.println("\nProduct Successfully Deleted!");
				}
		}
		
		public void exportProductInventory() {
				
				try {
						
						String exportedPath = Streamable.exportProductInventory(getListOfProducts());
						
						if (exportedPath == null) {
								System.out.println("Error occurred while Exporting File.");
								return;
						}
						
						System.out.println(FILE_SUCCESSFULLY_EXPORTED_POPUP + exportedPath);
						
				} catch (java.nio.file.FileSystemException e) {
						
						System.err.println(FILE_NOT_ACCESSIBLE_POPUP);
						
				} catch (IOException e) {
						
						e.printStackTrace();
				}
		}
		
		
		
		
//		                                             BILL METHODS
		
		
		
		
		
		private String generateBillId() {
				billNumber++;
				return String.format("%s-B%d", LocalDate.now(), billNumber);
		}
		
		
		private String selectBillId(String customerId) {
				
				String selectedBillId = null;
				
				if (!getListOfCustomers().get(customerId).getBills().isEmpty()) {
						
						int counter = 1;
						
						System.out.print("\nCopy & Paste a single Bill ID to select a Bill Below;\n\n");
						
						for (Bill bill : getListOfCustomers().get(customerId).getBills().values()) {
								System.out.println(counter++ + ": " + bill.getSpecificBillDetails());
						}
						
						System.out.print("\nPaste here: ");
						selectedBillId = inStr.nextLine();
						
				} else {
						System.out.println("\nNo Bill is associated with this Customer.");
				}
				
				return selectedBillId;
		}
		
		public void setBillPaidAmount() {
				
				String selectedCustomerId = selectCustomerId();
				String selectedBillId;
				
				if (selectedCustomerId != null) {
						selectedBillId = selectBillId(selectedCustomerId);
				} else {
						return;
				}
				
				if(selectedBillId == null) {
						return;
				}
				
				getListOfCustomers().get(selectedCustomerId).setPaidAmount(selectedBillId);
				getListOfCustomers().get(selectedCustomerId).setDebt();
				System.out.format("%nCustomer Debt: %.2f/-%n", getListOfCustomers().get(selectedCustomerId).getCustomerDebt());
				
		}
		public void setBillPaidAmount(String customerId, String billId) {
				getListOfCustomers().get(customerId).setPaidAmount(billId);
				getListOfCustomers().get(customerId).setDebt();
				System.out.format("%nCustomer Debt: %.2f/-%n", getListOfCustomers().get(customerId).getCustomerDebt());
				
		}
		
		public void viewAllBills() {
				
				int counter = 1;
				
				if (getListOfCustomers().isEmpty()) {
						System.out.println("\n No Customer Exists to View Bill for.");
						return;
				}
				for (Customer customer : getListOfCustomers().values()) {
						if (customer.getBills().isEmpty()) {
								continue;
						}
						for (Bill bill : customer.getBills().values()) {
								System.out.println(counter++ + "\n" + bill.getSpecificBillDetails());
						}
				}
				
				if (counter == 1) {
						System.out.println("\nNo Bill Exist to View.");
				}
		}
		
		public void viewSpecificBillDetails() {
		
				String selectedCustomerId = selectCustomerId();
				String selectedBillId;
				
				if (selectedCustomerId != null) {
						selectedBillId = selectBillId(selectedCustomerId);
				} else {
						return;
				}
				
				String specificBillDetails =
								getListOfCustomers().get(selectedCustomerId).getSpecificBillDetails(selectedBillId);
				
				System.out.println("\nList of Products\n" + specificBillDetails);
		}
		
		private long setQuantity(String productId) {
				
				long quantityToReturn = -1;
				
				if (productId.equalsIgnoreCase("done")) {
						return 0;
				}
				
				System.out.print("\nEnter the quantity: ");
				long quantity = in.nextLong();
				
				if (quantity <= getListOfProducts().get(productId).getRemainingStock()) {
						quantityToReturn = quantity;
				}
				
				return quantityToReturn;
		}
		
		public void setBill(String customerId) {
				
				String customerIdForSettingBill;
				
				if (customerId == null) {
						
						customerIdForSettingBill = selectCustomerId();
						
						if (customerIdForSettingBill == null) {
								return;
						}
						
				} else {
						customerIdForSettingBill = customerId;
				}
				
				String generatedBillId = generateBillId();
				
				// Now adding the list of Products to add into the bill
				Map<String, Long> selectedProductIdsToAddIntoBill = new LinkedHashMap<>();
				
				System.out.println("\nEnter 'Done' to stop entering more products.");
				String selectAProductIdAndCheckForDone;
				
				do {
						
						selectAProductIdAndCheckForDone = selectProductId();
						
						if (selectAProductIdAndCheckForDone == null) {
								return;
						}
						
						// Passed the productId in setQuantity to check if related stock is available
						long quantityToSet = setQuantity(selectAProductIdAndCheckForDone);
						
						if (quantityToSet > -1 && quantityToSet != 0) {
								selectedProductIdsToAddIntoBill.put(selectAProductIdAndCheckForDone, quantityToSet);
								getListOfProducts().get(selectAProductIdAndCheckForDone).setRemainingStock(-quantityToSet);
						} else if (quantityToSet == -1) {
								System.out.println("Expected stock quantity is not available.");
						}
						
				} while (!selectAProductIdAndCheckForDone.equalsIgnoreCase("done"));
				
				 if (!selectedProductIdsToAddIntoBill.keySet().toArray()[0].equals("done")) {
						
						 Map<Product, Long> listOfProductsToAddWithQuantity = new LinkedHashMap<>();
						 
						for (Map.Entry<String, Long> productEntry : selectedProductIdsToAddIntoBill.entrySet()) {
								listOfProductsToAddWithQuantity.put(getListOfProducts().get(productEntry.getKey()),
												productEntry.getValue());
						}
						
						Map<String, Bill> listOfBillToAddBillInTo = getListOfCustomers().get(customerIdForSettingBill).getBills();
						listOfBillToAddBillInTo.put(generatedBillId, new Bill(generatedBillId, listOfProductsToAddWithQuantity));
						
						setBillPaidAmount(customerIdForSettingBill, generatedBillId);
				}
		}
		
		public void viewSpecificCustomerBillDetails() {
				
				String selectedCustomerId = selectCustomerId();
				
				if (selectedCustomerId == null) {
						return;
				}
				
				System.out.println(getListOfCustomers().get(selectedCustomerId).getSpecificCustomerBillDetails());
		}
		
		public void deleteBill() {
				
				String selectedCustomerId = selectCustomerId();
				String selectedBillId;
				
				if (selectedCustomerId != null) {
						selectedBillId = selectBillId(selectedCustomerId);
				} else {
						return;
				}
				
				if(selectedBillId == null) {
						return;
				}
				
				getListOfCustomers().get(selectedCustomerId).deleteBill(selectedBillId);
				
				if (getListOfCustomers().get(selectedCustomerId).getBills().get(selectedBillId) == null) {
						System.out.println("\nBill Successfully Deleted!");
				}
		}
		
		public void exportBills() {
				
				
				try {
						
						String exportedPath = Streamable.exportBills(getListOfCustomers());
						
						if (exportedPath == null) {
								System.out.println("Error occurred while Exporting File.");
								return;
						}
						
						System.out.println(FILE_SUCCESSFULLY_EXPORTED_POPUP + exportedPath);
						
				} catch (java.nio.file.FileSystemException e) {
						
						System.err.println(FILE_NOT_ACCESSIBLE_POPUP);
						
				} catch (IOException e) {
						
						e.printStackTrace();
				}
		}
		
		
		
		
		
		
//		                                              CUSTOMER METHODS
		
		
		
		
		
		
		public String generateCustomerId() {
				customerNumber++;
				return String.format("%s-C%d", LocalDate.now(), customerNumber);
		}
		
		public void setCustomer() {
				
				String generatedCustomerId = generateCustomerId();
				
				System.out.print("\nEnter the Name of the Customer: ");
				String nameOfCustomer = inStr.nextLine().toUpperCase();
				
				System.out.print("Is Customer, the Patient (Yes/No): ");
				String selectedOptionString = inStr.nextLine();
				
				if (!(selectedOptionString.equalsIgnoreCase("yes") ||
								selectedOptionString.equalsIgnoreCase("no"))) {
						
						System.out.println("\nWrong Input, Try Again...");
						return;
				}
				
				boolean isAPatient = selectedOptionString.equalsIgnoreCase("yes");
				
				
				System.out.print("Enter the Name of Prescriber: ");
				String nameOfPrescriber = inStr.nextLine();
				
				System.out.print("Enter Diagnosed Disease: ");
				String diagnosedDisease = inStr.nextLine();
				
				getListOfCustomers().put(generatedCustomerId, new Customer(generatedCustomerId, isAPatient, nameOfCustomer,
								nameOfPrescriber, diagnosedDisease));
				
				System.out.print("\nDo you want to set a Bill for this Customer (Yes/No): ");
				String selectedOption = inStr.nextLine();
				
				if (!(selectedOption.equalsIgnoreCase("yes") || selectedOption.equalsIgnoreCase("no"))) {
						
						System.out.println("\nWrong Input, Try Again...");
						return;
				}
				
				if (selectedOption.equalsIgnoreCase("yes")) {
						setBill(generatedCustomerId);
						
				} else if (selectedOption.equalsIgnoreCase("no")) {
						System.out.println("\nCustomer Data has been entered Successfully but now manually enter bill for " +
										"this customer from Bill Menu.");
						
				} else {
						System.out.println("\nWrong Input, Customer Data is entered Successfully but now manually enter bill for " +
										"this customer from Bill Menu.");
				}
		}
		
		public String selectCustomerId() {
				
				String selectedCustomerId = null;
				
				System.out.print("\nEnter Name of the Customer: ");
				String nameOfCustomerToSearch = inStr.nextLine().toUpperCase();
				
				Map<String, String> matchedCustomerIds = getMatchingCustomerIds(nameOfCustomerToSearch);
				
				if (matchedCustomerIds.isEmpty()) {
						
						System.out.println("\nCustomer with this name don't Exist.");
						
				} else {
						
						System.out.println("\nCopy & Paste a single Customer ID to select a Customer Below;\n");
						
						for (Map.Entry<String, String> matchedCustomerEntry : matchedCustomerIds.entrySet()) {
								
								System.out.println(matchedCustomerEntry.getKey() + " : \t " + matchedCustomerEntry.getValue()
												.toUpperCase() + "\n");
						}
						
						System.out.print("Paste Here: ");
						selectedCustomerId = inStr.nextLine();
						
				}
				
				return selectedCustomerId;
		}
		
		public void viewAllCustomers() {
				
				if (getListOfCustomers().isEmpty()) {
						System.out.println("\nNo Customer Exist to Display.");
						return;
				} else {
						System.out.println();
				}
				
				int counter = 1;
				
				for (Customer customer : getListOfCustomers().values()) {
						System.out.println(counter++ + "\n" + customer.getCustomerBriefDetails());
				}
		}
		
		public void viewSpecificCustomerDetails() {
				
				String selectedCustomerId = selectCustomerId();
				
				if (selectedCustomerId == null) {
						return;
				}
				
				System.out.println("\n" + getListOfCustomers().get(selectedCustomerId).getSpecificCustomerDetails());
		}
		
		public void deleteCustomer() {
				
				String selectedCustomerId = selectCustomerId();
				
				if (selectedCustomerId != null) {
						
						getListOfCustomers().remove(selectedCustomerId);
						
						if (getListOfCustomers().get(selectedCustomerId) == null) {
								System.out.println("\nCustomer Successfully Deleted!");
						}
				}
		}
		
		public void exportCustomerList() {
				
				try {
						
						String exportedPath = Streamable.exportCustomerList(getListOfCustomers());
						
						if (exportedPath == null) {
								System.out.println("\nError occurred while Exporting File.");
								return;
						}
						
						System.out.println(FILE_SUCCESSFULLY_EXPORTED_POPUP + exportedPath);
						
				} catch (java.nio.file.FileSystemException e) {
						
						System.err.println(FILE_NOT_ACCESSIBLE_POPUP);
						
				} catch (IOException e) {
						
						e.printStackTrace();
				}
		}
}
