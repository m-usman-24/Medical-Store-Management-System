package pharmacy;

import lombok.Data;

import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Bill implements Dependable, Serializable {
		
		private String billId;
		private double totalWholeSaleCost;
		private double totalRetailCost;
		private double billAmountAfterDiscount;
		private double paidAmount;
		private float totalProfitMargin;
		private float totalProfitMarginAfterDiscount;
		private float discountPercentage;
		private Map<Product, Long> listOfPurchasedProductsWithQuantity = new LinkedHashMap<>();
		
		public Bill(String billId, Map<Product, Long> listOfProductWithQuantity) {
				
				setBillId(billId);
				
				for (Map.Entry<Product, Long> entryOfProductWithQuantity : listOfProductWithQuantity.entrySet()) {
						
						if (entryOfProductWithQuantity.getKey() == null) {
								continue;
						}
						
						listOfPurchasedProductsWithQuantity.put(entryOfProductWithQuantity.getKey(),
										entryOfProductWithQuantity.getValue());
						setTotalWholeSaleCost(entryOfProductWithQuantity.getKey().getWholeSalePrice()
										* entryOfProductWithQuantity.getValue());
						setTotalRetailCost(entryOfProductWithQuantity.getKey().getRetailPrice()
										* entryOfProductWithQuantity.getValue());
				}
				
				totalProfitMargin = (float) ((totalRetailCost - totalWholeSaleCost) / totalRetailCost * 100);
				
				System.out.println("\nTotal Wholesale cost: " + getTotalWholeSaleCost() + "/-");
				System.out.println("Total Retail cost: " + getTotalRetailCost() + "/-");
				System.out.format("Profit: %.2f%% | %.2f/-", totalProfitMargin, getTotalRetailCost() - getTotalWholeSaleCost());
				
				for (int i = 1 ; i <= 10 ; i++) {
						
						System.out.print("\n\nEnter the Discount Percentage: ");
						
						try {
								setTotalProfitMarginAfterDiscount(in.nextFloat());
								System.out.format("%nBill Amount after Discount: %.2f/-%n", billAmountAfterDiscount);
								break;
								
						} catch (DiscountInLossException e) {
								
								System.out.print("Discount is in Loss...");
								
								if (i == 10) {
										e.printStackTrace();
										System.exit(0);
								}
						}
				}
		}
		
		@Override
		public String toString() {
				
				return "Bill ID: " + billId + "\n" +
								"Total Wholesale Cost: " + totalWholeSaleCost + "\n" +
								"Total Retail Cost: " + totalRetailCost + "\n" +
								"Total Profit Margin: " + totalProfitMargin + "\n" +
								"Bill Amount after Discount: " + billAmountAfterDiscount + "\n" +
								String.format("Total Profit Margin after Discount: %.2f%%", totalProfitMarginAfterDiscount) + "\n" +
								"Discount Percentage: " + discountPercentage + "\n" +
								"Paid Amount: " + paidAmount;
		}
		
		public String getSpecificBillDetails() {
				return String.format("Bill ID: %s \t Bill: %.2f/- \t Paid: %.2f/- \t Due: %.2f/- \t Profit: %.2f%% | %.2f/-" +
												" \t Discount: %.0f%%", billId, billAmountAfterDiscount, paidAmount,
												Math.abs(billAmountAfterDiscount - paidAmount), totalProfitMarginAfterDiscount,
												billAmountAfterDiscount - totalWholeSaleCost, discountPercentage);
		}
		
		public void setTotalProfitMarginAfterDiscount(float discountedPercentage) throws DiscountInLossException {

				this.discountPercentage = discountedPercentage;
				billAmountAfterDiscount = totalRetailCost - ((discountPercentage / 100) * totalRetailCost);

				if (totalProfitMargin - discountedPercentage >= 0) {
						totalProfitMarginAfterDiscount =
										(float) ((billAmountAfterDiscount - totalWholeSaleCost) / totalRetailCost * 100);
						
				} else {
						throw new DiscountInLossException();
				}
		}

		public void setPaidAmount(double paidAmount) {
				this.paidAmount += paidAmount;
		}
		
		public void setTotalWholeSaleCost(double totalWholeSaleCost) {
				this.totalWholeSaleCost += totalWholeSaleCost;
		}
		
		public void setTotalRetailCost(double totalRetailCost) {
				this.totalRetailCost += totalRetailCost;
		}
		
		public static class DiscountInLossException extends InputMismatchException {
				@Override
				public String toString() {
						return "LOOP CONTINUES! Discounted Margin is under Loss.";
				}
		}
}
