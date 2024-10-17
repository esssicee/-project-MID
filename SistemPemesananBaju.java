import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class SistemPemesananBaju {
    private static Scanner input = new Scanner(System.in);
    private static String[] daftarBaju = {"Kaos", "Kemeja", "Jaket", "Celana", "Rok"};
    private static int[] hargaBaju = {50000, 100000, 200000, 150000, 120000};
    private static String[] ukuran = {"S", "M", "L", "XL"};
    private static HashMap<String, Integer> stok = new HashMap<>();
    private static ArrayList<Pesanan> daftarPesanan = new ArrayList<>();
    private static double totalPendapatan = 0;

    public static void main(String[] args) {
        initializeStok();
        boolean lanjut = true;
        while (lanjut) {
            tampilkanMenu();
            int pilihan = input.nextInt();
            switch (pilihan) {
                case 1:
                    buatPesanan();
                    break;
                case 2:
                    lihatDaftarPesanan();
                    break;
                case 3:
                    cekStok();
                    break;
                case 4:
                    tambahStok();
                    break;
                case 5:
                    ubahHarga();
                    break;
                case 6:
                    lihatLaporanPenjualan();
                    break;
                case 7:
                    cariPesanan();
                    break;
                case 8:
                    hapusPesanan();
                    break;
                case 0:
                    lanjut = false;
                    System.out.println("Terima kasih telah menggunakan Go-cloth!");
                    break;
                default:
                    System.out.println("Tidak adaa pilihan. cobaa lagi ya :).");
            }
        }
    }

    private static void tampilkanMenu() {
        System.out.println("\nSelamat datang di Sistem Pemesanan Baju");
        System.out.println("1. Buat Pesanan Baru");
        System.out.println("2. Lihat Daftar Pesanan");
        System.out.println("3. Cek Stok");
        System.out.println("4. Tambah Stok");
        System.out.println("5. Ubah Harga");
        System.out.println("6. Lihat Laporan Penjualan");
        System.out.println("7. Cari Pesanan");
        System.out.println("8. Hapus Pesanan");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu (0-8): ");
    }

    private static void buatPesanan() {
        System.out.println("\nDaftar Baju:");
        for (int i = 0; i < daftarBaju.length; i++) {
            System.out.println((i+1) + ". " + daftarBaju[i] + " - Rp" + hargaBaju[i]);
        }
        System.out.print("Pilih nomor baju (1-" + daftarBaju.length + "): ");
        int pilihBaju = input.nextInt() - 1;

        System.out.println("\nUkuran yang tersedia: ");
        for (int i = 0; i < ukuran.length; i++) {
            System.out.println((i+1) + ". " + ukuran[i]);
        }
        System.out.print("Pilih nomor ukuran (1-" + ukuran.length + "): ");
        int pilihUkuran = input.nextInt() - 1;

        System.out.print("\nMasukkan jumlah baju yang ingin dipesan: ");
        int jumlah = input.nextInt();

        String kodeBarang = daftarBaju[pilihBaju] + "-" + ukuran[pilihUkuran];
        if (stok.get(kodeBarang) < jumlah) {
            System.out.println("Maaf, stok tidak mencukupi.");
            return;
        }

        int totalHarga = hargaBaju[pilihBaju] * jumlah;

        System.out.println("\nRincian Pesanan:");
        System.out.println("Baju: " + daftarBaju[pilihBaju]);
        System.out.println("Ukuran: " + ukuran[pilihUkuran]);
        System.out.println("Jumlah: " + jumlah);
        System.out.println("Total Harga: Rp" + totalHarga);

        System.out.print("Konfirmasi pesanan (y/n): ");
        String konfirmasi = input.next();
        if (konfirmasi.equalsIgnoreCase("y")) {
            Pesanan pesananBaru = new Pesanan(daftarBaju[pilihBaju], ukuran[pilihUkuran], jumlah, totalHarga);
            daftarPesanan.add(pesananBaru);
            stok.put(kodeBarang, stok.get(kodeBarang) - jumlah);
            totalPendapatan += totalHarga;
            System.out.println("Pesanan berhasil dibuat!");
        } else {
            System.out.println("Pesanan dibatalkan.");
        }
    }

    private static void lihatDaftarPesanan() {
        if (daftarPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan.");
        } else {
            System.out.println("\nDaftar Pesanan:");
            for (int i = 0; i < daftarPesanan.size(); i++) {
                System.out.println("Pesanan #" + (i+1));
                System.out.println(daftarPesanan.get(i));
                System.out.println();
            }
        }
    }

    private static void cekStok() {
        System.out.println("\nStok Baju:");
        for (String baju : daftarBaju) {
            for (String size : ukuran) {
                String kodeBarang = baju + "-" + size;
                System.out.println(kodeBarang + ": " + stok.get(kodeBarang));
            }
        }
    }

    private static void tambahStok() {
        System.out.println("\nPilih baju untuk menambah stok:");
        for (int i = 0; i < daftarBaju.length; i++) {
            System.out.println((i+1) + ". " + daftarBaju[i]);
        }
        System.out.print("Pilih nomor baju: ");
        int pilihBaju = input.nextInt() - 1;

        System.out.println("\nPilih ukuran:");
        for (int i = 0; i < ukuran.length; i++) {
            System.out.println((i+1) + ". " + ukuran[i]);
        }
        System.out.print("Pilih nomor ukuran: ");
        int pilihUkuran = input.nextInt() - 1;

        System.out.print("Masukkan jumlah stok yang ditambahkan: ");
        int jumlahTambah = input.nextInt();

        String kodeBarang = daftarBaju[pilihBaju] + "-" + ukuran[pilihUkuran];
        stok.put(kodeBarang, stok.get(kodeBarang) + jumlahTambah);
        System.out.println("Stok berhasil ditambahkan!");
    }

    private static void ubahHarga() {
        System.out.println("\nPilih baju untuk mengubah harga:");
        for (int i = 0; i < daftarBaju.length; i++) {
            System.out.println((i+1) + ". " + daftarBaju[i] + " - Rp" + hargaBaju[i]);
        }
        System.out.print("Pilih nomor baju: ");
        int pilihBaju = input.nextInt() - 1;

        System.out.print("Masukkan harga baru: Rp");
        int hargaBaru = input.nextInt();

        hargaBaju[pilihBaju] = hargaBaru;
        System.out.println("Harga berhasil diubah!");
    }

    private static void lihatLaporanPenjualan() {
        System.out.println("\nLaporan Penjualan:");
        System.out.println("Total Pesanan: " + daftarPesanan.size());
        System.out.println("Total Pendapatan: Rp" + totalPendapatan);

        System.out.println("\nRincian Penjualan per Jenis Baju:");
        for (String baju : daftarBaju) {
            int jumlahTerjual = 0;
            double pendapatanPerBaju = 0;
            for (Pesanan pesanan : daftarPesanan) {
                if (pesanan.getBaju().equals(baju)) {
                    jumlahTerjual += pesanan.getJumlah();
                    pendapatanPerBaju += pesanan.getTotalHarga();
                }
            }
            System.out.println(baju + ": " + jumlahTerjual + " pcs - Rp" + pendapatanPerBaju);
        }
    }

    private static void cariPesanan() {
        System.out.print("Masukkan nomor pesanan: ");
        int nomorPesanan = input.nextInt() - 1;

        if (nomorPesanan >= 0 && nomorPesanan < daftarPesanan.size()) {
            System.out.println("\nDetail Pesanan:");
            System.out.println(daftarPesanan.get(nomorPesanan));
        } else {
            System.out.println("Pesanan tidak ditemukan.");
        }
    }

    private static void hapusPesanan() {
        System.out.print("Masukkan nomor pesanan yang akan dihapus: ");
        int nomorPesanan = input.nextInt() - 1;

        if (nomorPesanan >= 0 && nomorPesanan < daftarPesanan.size()) {
            Pesanan pesananDihapus = daftarPesanan.remove(nomorPesanan);
            String kodeBarang = pesananDihapus.getBaju() + "-" + pesananDihapus.getUkuran();
            stok.put(kodeBarang, stok.get(kodeBarang) + pesananDihapus.getJumlah());
            totalPendapatan -= pesananDihapus.getTotalHarga();
            System.out.println("Pesanan berhasil dihapus.");
        } else {
            System.out.println("Pesanan tidak ditemukan.");
        }
    }

    private static void initializeStok() {
        for (String baju : daftarBaju) {
            for (String size : ukuran) {
                stok.put(baju + "-" + size, 50);
            }
        }
    }
}

class Pesanan {
    private String baju;
    private String ukuran;
    private int jumlah;
    private int totalHarga;

    public Pesanan(String baju, String ukuran, int jumlah, int totalHarga) {
        this.baju = baju;
        this.ukuran = ukuran;
        this.jumlah = jumlah;
        this.totalHarga = totalHarga;
    }

    public String getBaju() { return baju; }
    public String getUkuran() { return ukuran; }
    public int getJumlah() { return jumlah; }
    public int getTotalHarga() { return totalHarga; }

    @Override
    public String toString() {
        return "Baju: " + baju + "\nUkuran: " + ukuran + "\nJumlah: " + jumlah + "\nTotal Harga: Rp" + totalHarga;
    }
}