package pharmacy;

import java.io.File;

public class MedicalStoreRunner implements Streamable, Dependable {
		public static void main(String[] args) {
				
				Owner owner = new Owner();
				MedicalStore medicalStore = new MedicalStore();
				
				if (new File(Streamable.getCustomSystemDirectory() + "\\SerializedOwner.bin").isFile()) {
						owner = (Owner) Streamable.deserialize(owner);
				} else {
						owner = new Owner(5);
				}
				
				if (new File(Streamable.getCustomSystemDirectory() + "\\SerializedMedicalStore.bin").isFile()) {
						medicalStore = (MedicalStore) Streamable.deserialize(medicalStore);
				}
				
				owner.validateLogin(owner);
				
				for ( ; ; ) {
						
						System.out.println("\n<-< MAIN MENU >->");
						
						System.out.println("1. OWNER PANEL.");
						System.out.println("2. PRODUCT MENU.");
						System.out.println("3. CUSTOMER MENU.");
						System.out.println("4. BILL MENU.");
						System.out.println("0. SAVE & EXIT.");
						
						System.out.print("<--------------->\n");
						
						System.out.print("\nSelect Option: ");
						char selectOption = in.next().charAt(0);
						
						switch (selectOption) {
								
								case '0': {
										Streamable.serialize(owner);
										Streamable.serialize(medicalStore);
										System.out.println("SAVING SUCCESSFUL!");
										System.exit(0);
										return;
								}
								
								case '1':
										Menu.ownerPanel(owner);
										break;
								
								case '2':
										Menu.productMenu(medicalStore);
										break;
								
								case '3':
										Menu.customerMenu(medicalStore);
										break;
								
								case '4':
										Menu.billMenu(medicalStore);
										break;
								
								default : System.out.println("\nWrong Selection, Try Again!\n");
						}
				}
		}
}
