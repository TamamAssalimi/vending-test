package org.example;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        ProductService productService = new ProductService();
        productService.init();
        Main main = new Main();
        main.machineController(productService);

    }

    public void machineController(ProductService productService) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("""
                vending machine\t
                 1.List Menu Produk \t 2.Pembelian \t 3.Cek Stok
                 masukan pilihan :\s""");
        int option1 = scanner.nextInt();
        if (option1 == 1)
            System.out.println("1.List Menu Produk");
        else if (option1 == 2)
            System.out.println("2.Pembelian");
        else if (option1 == 3)
            System.out.println("3.Cek Stok");
        else {
            System.out.println("Pilihan tidak ditemukan");
            machineController(productService);
        }

        if (option1 == 1) {
            Map<Long, Product> productMap = productService.getAllProduct();
            for (int i = 1; i <= productMap.size(); i++) {
                Product p = productMap.get((long) i);
                System.out.println(p.getId() + ".\t" + p.getProductName() + "\tHarga:" + p.getPrice() + "\tStok:" + p.getStock());
            }
            scanner = new Scanner(System.in);
            System.out.print("Melanjutkan Ke- Pembelian (Y/N) : ");
            String option2 = scanner.nextLine();
            if (option2.equalsIgnoreCase("Y")) {
                doPurchase(productService);
            } else {
                machineController(productService);
            }

        } else if (option1 == 2) {
            doPurchase(productService);
        } else {
            checkStok(productService);
        }
    }

    public void doPurchase(ProductService productService) {
        System.out.print("Masukan Nomor Produk : ");
        Scanner scanner = new Scanner(System.in);
        int option1 = scanner.nextInt();
        Product p = productService.getProductById((long) option1);
        if (null == p || 0 >= p.getStock()) {
            System.out.println("Produk Tidak Tersedia");
            machineController(productService);
        } else {
            System.out.println(p.getId() + ".\t" + p.getProductName() + "\tHarga:" + p.getPrice() + "\tStok:" + p.getStock());
            doPayment(p, productService);
        }
    }

    public void doPayment(Product p, ProductService productService) {
        System.out.print("Masukan Nominal Uang : ");
        Scanner scanner = new Scanner(System.in);
        double amount = scanner.nextDouble();
        if (!inputNominalValidation(amount)) {
            System.out.println("Nominal Tidak Tersedia");
            doPayment(p, productService);
        }
        if (p.getPrice() > amount) {
            System.out.println("Jumlah Uang Tidak Mencukupi");
            doPayment(p, productService);
        } else {
            System.out.println("Detail Pembayaran" +
                    "\n Nama Produk : "+p.getProductName()+
                    "\n Harga :"+p.getPrice()+
                    "\n Jumlah Uang Yang Dibayar :"+amount+
                    "\n Jumlah Uang Kembali :"+(amount-p.getPrice()));

            System.out.print("Lanjutkan Proses Pembayaran (Y/N):");
            Scanner scanner1 = new Scanner(System.in);
            String option2 = scanner1.nextLine();
            if(option2.equalsIgnoreCase("Y")){
                System.out.println("Pembayaran sedang diproses");
                productService.updateProduct(p.getId(), new Product(p.getId(), p.getProductName(), p.getPrice(), p.getStock() - 1));
                System.out.println("Pembayaran Berhasil... " +
                        "\nKembali Ke Menu Utama" +
                        "\n==================================================");
                machineController(productService);
            }else{
                doPayment(p,productService);
            }
        }
    }

    public boolean inputNominalValidation(double input) {
        double[] nominals = {2000.0, 5000.0, 10000.0, 20000.0, 50000.0};
        return Arrays.stream(nominals).anyMatch(x -> x == input);
    }

    public void checkStok(ProductService productService) {
        System.out.print("1.Periksa Stok Produk Tertentu \t 2.Periksa Semua Produk \n Masukan Pilihan :");
        Scanner scanner = new Scanner(System.in);
        int option1 = scanner.nextInt();
        if (option1 == 2) {
            Map<Long, Product> productMap = productService.getAllProduct();
            for (int i = 1; i <= productMap.size(); i++) {
                Product p = productMap.get((long) i);
                System.out.println(p.getId() + ".\t" + p.getProductName() + "\tStok:" + p.getStock());
            }
        } else if (option1 == 1) {
            System.out.print("Masukan Nomor Produk : ");
            int option2 = scanner.nextInt();
            Product p = productService.getProductById((long) option2);
            System.out.println(p.getId() + ".\t" + p.getProductName() + "\tStok:" + p.getStock());
        }else {
            checkStok(productService);
        }
        System.out.print("Kembali Ke Manu Utama");
        machineController(productService);
    }
}