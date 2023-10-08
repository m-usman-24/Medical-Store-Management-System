package pharmacy;

public class Menu implements Dependable {
		
		private static final String WRONG_SELECTION_POPUP = "\nWrong Selection, Try Again!\n";
		private static final String RETURN_TO_MAIN_MENU = "0. Return to Main Menu.";
		private static final String SELECT_OPTION = "\nSelect Option: ";
		
		private Menu() {
		
		}
		
		public static void ownerPanel(Owner owner) {
				
				for ( ; ; ) {
						
						System.out.println("\n1. Update Owner Name.");
						System.out.println("2. Update Password.");
						System.out.println("3. Set or Update Contact Number.");
						System.out.println("4. View Last 10 Login Stamps.");
						System.out.println(RETURN_TO_MAIN_MENU);
						
						System.out.print(SELECT_OPTION);
						char selectedOption = in.next().charAt(0);
						
						switch (selectedOption) {
								
								case '0' : return;
								
								case '1' : owner.updateOwnerName();
								break;
								
								case '2' : owner.updatePassword();
								break;
								
								case '3' : owner.setOrUpdateContactNumber();
								break;
								
								case '4' : owner.getLast5LoginStamps();
								break;
								
								default : System.out.println(WRONG_SELECTION_POPUP);
						}
				}
		}
		
		public static void productMenu(MedicalStore medicalStore) {
				
				for ( ; ; ) {
						
						System.out.println("\n1. Set Product.");
						System.out.println("2. View Expired Batch Details.");
						System.out.println("3. Search a Specific Product.");
						System.out.println("4. View Product Inventory.");
						System.out.println("5. Product Re-stock.");
						System.out.println("6. Delete Product.");
						System.out.println("7. Export Product Inventory.");
						System.out.println(RETURN_TO_MAIN_MENU);
						
						System.out.print(SELECT_OPTION);
						char selectOption = in.next().charAt(0);
						
						switch (selectOption) {
								
								case '0' : return;
								
								case '1' : medicalStore.setProduct();
								break;
								
								case '2' : medicalStore.viewExpiredBatchDetails();
								break;
								
								case '3' : medicalStore.searchASpecificProduct();
								break;
								
								case '4' : medicalStore.viewProductInventory();
								break;
								
								case '5' : medicalStore.productRestock();
								break;
								
								case '6' : medicalStore.deleteProduct();
								break;
								
								case '7' : medicalStore.exportProductInventory();
								break;
								
								default : System.out.println(WRONG_SELECTION_POPUP);
						}
				}
		}
		
		public static void customerMenu(MedicalStore medicalStore) {
				
				for ( ; ; ) {
						
						System.out.println("\n1. Add a New Customer.");
						System.out.println("2. View Specific Customer Details.");
						System.out.println("3. View All Customers.");
						System.out.println("4. Delete Customer.");
						System.out.println("5. Export Customer List.");
						System.out.println(RETURN_TO_MAIN_MENU);
						
						System.out.print(SELECT_OPTION);
						char selectOption = in.next().charAt(0);
						
						switch (selectOption) {
								
								case '0' : return;
								
								case '1' : medicalStore.setCustomer();
								break;
								
								case '2' : medicalStore.viewSpecificCustomerDetails();
								break;
								
								case '3' : medicalStore.viewAllCustomers();
								break;
								
								case '4' : medicalStore.deleteCustomer();
								break;
								
								case '5' : medicalStore.exportCustomerList();
								break;
								
								default : System.out.println(WRONG_SELECTION_POPUP);
						}
				}
		}
		
		public static void billMenu(MedicalStore medicalStore) {
				
				for ( ; ; ) {
						
						System.out.println("\n1. Add a New Bill.");
						System.out.println("2. Set-up Bill Paid Amount.");
						System.out.println("3. View Specific Customer Bill Details.");
						System.out.println("4. View Specific Bill Details.");
						System.out.println("5. View All Bills.");
						System.out.println("6. Delete Bill.");
						System.out.println("7. Export Bills.");
						System.out.println(RETURN_TO_MAIN_MENU);
						
						System.out.print(SELECT_OPTION);
						char selectOption = in.next().charAt(0);
						
						switch (selectOption) {
								
								case '0' : return;
								
								case '1' : medicalStore.setBill(null);
								break;
								
								case '2' : medicalStore.setBillPaidAmount();
								break;
								
								case '3' : medicalStore.viewSpecificCustomerBillDetails();
								break;
								
								case '4' : medicalStore.viewSpecificBillDetails();
								break;
								
								case '5' : medicalStore.viewAllBills();
								break;
								
								case '6' : medicalStore.deleteBill();
								break;
								
								case '7' : medicalStore.exportBills();
								break;
								
								default : System.out.println(WRONG_SELECTION_POPUP);
						}
				}
		}
}
