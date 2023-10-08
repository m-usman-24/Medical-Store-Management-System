package pharmacy;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Customer implements Dependable, Serializable {
		
		private String customerId;
		private String nameOfCustomer;
		private boolean customerAPatient;
		private String patientName;
		private String nameOfPrescriber;
		private String diagnosedDisease;
		private double customerDebt;
		private Map<String, Bill> bills = new LinkedHashMap<>();
		
		public Customer(String customerId, boolean customerAPatient, String nameOfCustomer, String nameOfPrescriber,
		                String diagnosedDisease) {
				
				if (customerAPatient) {
						setPatientName(nameOfCustomer);
				} else {
						System.out.print("Enter the name of the Patient: ");
						setPatientName(inStr.nextLine().toUpperCase());
				}
				
				setCustomerId(customerId);
				setCustomerAPatient(customerAPatient);
				setNameOfCustomer(nameOfCustomer);
				setNameOfPrescriber(nameOfPrescriber);
				setDiagnosedDisease(diagnosedDisease);
		}
		
		@Override
		public String toString() {
				return "Customer Name: " + nameOfCustomer + "\n" +
								"Patient Name: " + patientName + "\n" +
								"Prescriber Name: " + nameOfPrescriber + "\n" +
								"Diagnosed Disease: " + diagnosedDisease + "\n" +
								"Customer Debt: " + customerDebt + "\n" +
								"Bill Details are Below: " + bills;
		}
		
		public String getCustomerBriefDetails() {
				return String.format("Customer ID: %s\tCustomer Name: %s\tCustomer Debt: %.2f/-", customerId, nameOfCustomer,
								customerDebt);
		}
		
		public String getSpecificCustomerDetails() {
				
				return "Customer ID: " + customerId + " \t Customer Name: " + nameOfCustomer + " \t Customer Debt: " +
								String.format("%.2f/-", customerDebt) +
								"\nPatient Name: " + patientName + " \t Prescriber Name: " + nameOfPrescriber + "\t Diagnosed " +
								"Disease: " + diagnosedDisease;
		}
		
		public String getSpecificCustomerBillDetails() {
				
				StringBuilder specificCustomerBillDetails = new StringBuilder();
				String customerBillStatus;
				
				double totalWholesalePrice = 0;
				double totalRetailPrice = 0;
				double totalDiscountPercentage = 0;
				double totalAmountAfterDiscount = 0;
				double totalPaidAmount = 0;
				double totalProfitMarginAfterDiscount;
				double totalPendingDues = 0;
				
				int counter = 1;
				
				System.out.println();
				
				for (Bill bill : bills.values()) {
						
						if (bill == null) {
								continue;
						}
						
						specificCustomerBillDetails.append(counter++).append("\n").append(bill.getSpecificBillDetails())
										.append("\n");
						
						totalWholesalePrice += bill.getTotalWholeSaleCost();
						totalRetailPrice += bill.getTotalRetailCost();
						totalDiscountPercentage += bill.getDiscountPercentage();
						totalAmountAfterDiscount += bill.getBillAmountAfterDiscount();
						totalPaidAmount += bill.getPaidAmount();
						totalPendingDues += Math.abs(bill.getBillAmountAfterDiscount() - bill.getPaidAmount());
						
				}
				
				if (totalRetailPrice == 0) {
						System.err.println("\ntotalRetailPrice encountered to be null in Customer class @L97");
						return null;
				}
				
				totalProfitMarginAfterDiscount =
								(totalAmountAfterDiscount - totalWholesalePrice) / totalRetailPrice * 100;
				
				totalDiscountPercentage = totalDiscountPercentage / bills.size();
								
				customerBillStatus = String.format("\nTotal Retail Price: Rs. %.2f/-\nTotal Discount Percentage: %.2f%%" +
												"\nTotal Bill Amount After Discount: Rs. %.2f/-\nTotal Profit Margin After Discount: %.2f%%" +
												"\nTotal Paid Amount: Rs. %.2f/-\nTotal Pending Dues: Rs. %.2f/-", totalRetailPrice,
												totalDiscountPercentage, totalAmountAfterDiscount, totalProfitMarginAfterDiscount,
												totalPaidAmount, totalPendingDues);
				
				return specificCustomerBillDetails + customerBillStatus;
		}
		
		public String getSpecificBillDetails(String billId) {
				
				StringBuilder specificBillDetails = new StringBuilder();
				int counter = 1;
				
				for (Map.Entry<Product, Long> productWithQuantity :
								bills.get(billId).getListOfPurchasedProductsWithQuantity().entrySet()) {
						
						if (productWithQuantity == null) {
								continue;
						}
						
						Product product = productWithQuantity.getKey();
						long productQty = productWithQuantity.getValue();
						
						double wholeSalePrice = product.getWholeSalePrice() * productQty;
						double retailPrice = product.getRetailPrice() * productQty;
						double profit = retailPrice - wholeSalePrice;
						float profitMargin = (float) (profit / retailPrice * 100);
						
						specificBillDetails.append("\n").append(counter++).append(": ").append(product.getNameOfProduct())
										.append(" ").append(product.getPotency()).append(product.getUnitOfMeasure()).append(" \t QTY: ")
										.append(productQty).append(String.format(" \t Retail: %.2f/- \t Profit: %.2f/- \t Profit" +
										"Margin: %.2f%%", retailPrice, profit, profitMargin));
				}
				
				specificBillDetails.append("\n\n").append(bills.get(billId).getSpecificBillDetails());
				
				return specificBillDetails.toString();
		}

		public void deleteBill(String billId) {
				
				if (bills.containsKey(billId)) {
						bills.remove(billId);
				} else {
						System.out.println("Bill with this ID doesn't exist.");
				}
		}
		
		public void setDebt() {
				
				customerDebt = 0;
				
				for (Bill bill : bills.values()) {
						
						if (bill.getBillAmountAfterDiscount() - bill.getPaidAmount() >= 0) {
								customerDebt += Math.abs(bill.getBillAmountAfterDiscount() - bill.getPaidAmount());
						}
				}
		}
		
		public void setPaidAmount(String billId) {
				
				
				if (!bills.containsKey(billId)) {
						System.out.println("Bill with this ID doesn't exist.");
						return;
				}
						
				System.out.print("\nEnter the Paid Amount of Bill: ");
				double paidAmount = in.nextDouble();
				
				double billAmountAfterDiscount = bills.get(billId).getBillAmountAfterDiscount();
				double billPaidAmount = bills.get(billId).getPaidAmount();
				double billDebtAmount = Math.ceil(billAmountAfterDiscount - billPaidAmount);
				
				if (paidAmount > 0 && paidAmount <= billDebtAmount) {
						
						bills.get(billId).setPaidAmount(paidAmount);
						System.out.println("\nPaid amount set Successful.");
						
				} else {
						System.out.println("\nPaid amount is > actual bill amount OR <= '0',\n" +
										"Now Try Again from the Bill Menu.");
				}
				
		}
}
