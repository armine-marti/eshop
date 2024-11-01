package org.example.eshop;

import org.example.eshop.service.CategoryService;
import org.example.eshop.service.EshopCommands;
import org.example.eshop.service.ProductService;
import org.example.eshop.model.Category;
import org.example.eshop.model.Product;


import java.util.Scanner;

public class Eshop implements EshopCommands {

    private static Scanner scanner = new Scanner(System.in);
    private static CategoryService categoryService = new CategoryService();
    private static ProductService productService = new ProductService();

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            EshopCommands.printCommands();
            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case ADD_CATEGORY:
                    addCategory();
                    break;
                case EDIT_CATEGORY_BY_ID:
                    editCategory();
                    break;
                case DELETE_CATEGORY_BY_ID:
                    deleteCategory();
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case EDIT_PRODUCT_BY_ID:
                    editProduct();
                    break;
                case DELETE_PRODUCT_BY_ID:
                    deleteProduct();
                    break;
                case PRINT_SUM_OF_PRODUCTS:
                    printProductSum();
                    break;
                case PRINT_MAX_OF_PRICE_PRODUCT:
                    printMaxPrice();
                    break;
                case PRINT_MIN_OF_PRICE_PRODUCT:
                    printMinProductPrice();
                    break;
                case PRINT_AVG_OF_PRICE_PRODUCT:
                    printAvgProductPrice();
                    break;
                default:
                    System.out.println("Wrong command!");
            }
        }

    }

    private static void printAvgProductPrice() {
        double averagePrice = productService.getAvgProductPrice();
        System.out.println("The average price of all products is " + averagePrice + " £");

    }

    private static void printMinProductPrice() {
        Product product = new Product();
        product = productService.getMinPriceProduct();
        System.out.println("The cheapest product is " + product);
        System.out.println("Its price is " + product.getPrice() + " £");
    }

    private static void printMaxPrice() {
        Product product = new Product();
        product = productService.getMaxPriceProduct();
        System.out.println("The most expensive product is " + product);
        System.out.println("Its price is " + product.getPrice() + " £");
    }

    private static void printProductSum() {
        int i = productService.getSumOfProducts();
        System.out.println("Total quantity of all product is " + i);
    }

    private static void deleteProduct() {
        System.out.println(productService.getAllProducts());
        System.out.println("Please input product ID you want to delete");
        int id = Integer.parseInt(scanner.nextLine());
        Product product = productService.getProductById(id);
        if (product != null) {
            productService.deleteProduct(product.getId());
            System.out.println("Product deleted!");
        } else System.out.println("Incorrect ID, try again!");
    }

    private static void editProduct() {
        System.out.println(productService.getAllProducts());
        System.out.println("Please enter product ID you want to update");
        int productId = Integer.parseInt(scanner.nextLine());
        Product product = productService.getProductById(productId);
        if (product != null) {
            System.out.println("Please input product's new name");
            String name = scanner.nextLine();
            System.out.println("Please input product's new price");
            String priceStr = scanner.nextLine();
            System.out.println("Please input product's new quantity");
            String quantityStr = scanner.nextLine();
            System.out.println("Please input new category name");
            String categoryStr = scanner.nextLine();
            Category category = categoryService.getCategoryByName(categoryStr);
            if (category != null) {
                product.setCategory(category);
            } else {
                System.out.println("Category not found, try again!");
                return;
            }
            System.out.println("Please input product's description");
            String descriptionStr = scanner.nextLine();
            if (name != null && !name.isEmpty()) {
                product.setName(name);
            }
            try {
                if (priceStr != null && !priceStr.isEmpty()) {
                    product.setPrice(Double.parseDouble(priceStr));
                }
                if (!quantityStr.isEmpty()) {
                    product.setQuantity(Integer.parseInt(quantityStr));
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numbers!");
            }
            if (descriptionStr != null && !descriptionStr.isEmpty()) {
                product.setDescription(descriptionStr);
            }
            productService.updateProduct(product);
            System.out.println("Update was successfully");
        }
    }


    private static void addProduct() {
        System.out.println(categoryService.getAllCategories());
        System.out.println("Please choose category ID");
        int id = Integer.parseInt(scanner.nextLine());
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            System.out.println("Please input product's name, price, quantity");
            String productDataStr = scanner.nextLine();
            String[] productDataArr = productDataStr.split(",");
            if (productDataArr.length == 3) {
                try {
                    Product product = new Product();
                    product.setName(productDataArr[0]);
                    double price = Double.parseDouble(productDataArr[1]);
                    int quantity = Integer.parseInt(productDataArr[2]);
                    product.setPrice(price);
                    product.setQuantity(quantity);
                    product.setCategory(category);

                    System.out.println("Product added!!!");
                    System.out.println("Please press ENTER if you want to add description for product");
                    String descriptionConfirmation = scanner.nextLine();
                    if (descriptionConfirmation.isBlank()) {
                        System.out.println("Please input product description");
                        String description = scanner.nextLine();
                        product.setDescription(description);
                        System.out.println("Description was successfully added to the product");
                        productService.add(product);
                    } else {
                        System.out.println("Wrong input! Please input only Enter!");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else System.out.println("Wrong category ID, try again!");

    }

    private static void deleteCategory() {
        System.out.println(categoryService.getAllCategories());
        System.out.println("Please input category ID you want to delete");
        int categoryId = Integer.parseInt(scanner.nextLine());
        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            categoryService.deleteCategory(categoryId);
            System.out.println("Category deleted successfully!");
            System.out.println(categoryService.getAllCategories());
        } else System.out.println("Make sure your category ID is valid and typed correctly");
    }

    private static void editCategory() {
        System.out.println(categoryService.getAllCategories());
        System.out.println("Please input category ID you want to update");
        int categoryId = Integer.parseInt(scanner.nextLine());
        Category category = categoryService.getCategoryById(categoryId);
        if (category != null) {
            System.out.println("Please enter new name for category");
            String name = scanner.nextLine();
            if (name != null && !name.isEmpty()) {
                category.setName(name);
                categoryService.updateCategory(category);
                System.out.println("Update was successfully");
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
    }


    private static void addCategory() {
        System.out.println("Please enter the name of the category: ");
        String name = scanner.nextLine();
        Category category = categoryService.getCategoryByName(name);
        if (category == null) {
            Category newCategory = new Category(name);
            categoryService.add(newCategory);
            System.out.println("Category added!");
            System.out.println(categoryService.getAllCategories());
        } else System.out.println("Category with that name already exists!");
    }

}