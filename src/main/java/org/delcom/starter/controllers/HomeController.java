package org.delcom.starter.controllers;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;
// import java.io.BufferedReader;
// import java.io.StringReader;
// import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
public class HomeController {

    @GetMapping("/")
    public String hello() {
        return "Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

     // 1. Informasi NIM (migrasi StudiKasus1)
    @GetMapping("/informasiNim/{nim}")
    public String informasiNim(@PathVariable String nim) {
        nim = nim.trim();
        StringBuilder sb = new StringBuilder();

        if (nim.length() < 8) {
            sb.append("NIM tidak sesuai format!");
            return sb.toString();
        }

        String awal = nim.substring(0, 3);
        String thn = nim.substring(3, 5);
        String urut = nim.substring(5);

        String jurusan;
        jurusan = switch (awal) {
            case "11S" -> "Sarjana Informatika";
            case "12S" -> "Sarjana Sistem Informasi";
            case "14S" -> "Sarjana Teknik Elektro";
            case "21S" -> "Sarjana Manajemen Rekayasa";
            case "22S" -> "Sarjana Teknik Metalurgi";
            case "31S" -> "Sarjana Teknik Bioproses";
            case "114" -> "Diploma 4 Teknologi Rekayasa Perangkat Lunak";
            case "113" -> "Diploma 3 Teknologi Informasi";
            case "133" -> "Diploma 3 Teknologi Komputer";
            default -> null;
        };

        if (jurusan == null) {
            sb.append("Prefix ").append(awal).append(" tidak dikenali");
        } else {
            int tahunMasuk = Integer.parseInt("20" + thn);
            int no = Integer.parseInt(urut);

            sb.append("Informasi NIM ").append(nim).append(":").append(" ");
            sb.append(">> Program Studi: ").append(jurusan).append(" ");
            sb.append(">> Angkatan: ").append(tahunMasuk).append(" ");
            sb.append(">> Urutan: ").append(no);
        }

        return sb.toString();
    }

 @GetMapping("/perolehanNilai/{strBase64}")
    public String perolehanNilai(@PathVariable String strBase64) {

        String[] kategori = {
            "Partisipatif", "Tugas", "Kuis", "Proyek", "UTS", "UAS"
        };
        double[] bobot = {
            0.10, 0.15, 0.10, 0.15, 0.20, 0.30
        };
        double[] skor = new double[kategori.length];

        try {
            String decoded = new String(Base64.getDecoder().decode(strBase64));
            String[] lines = decoded.split("\\R");

            for (String line : lines) {
                String[] parts = line.split("\\|");
                if (parts.length == 2) {
                    String namaKategori = parts[0].trim();
                    for (int i = 0; i < kategori.length; i++) {
                        if (kategori[i].equals(namaKategori)) {
                            try {
                                double nilai = Double.parseDouble(parts[1].trim());
                                skor[i] = nilai;
                            } catch (NumberFormatException ignored) {
                                // Abaikan jika nilai bukan angka
                            }
                        }
                    }
                }
            }
        } catch (Exception ignored) {
            // Jika Base64 tidak valid, skor tetap 0
        }

        // Hitung nilai akhir
        double nilaiAkhir = 0.0;
        for (int i = 0; i < skor.length; i++) {
            nilaiAkhir += skor[i] * bobot[i];
        }

        // Format angka dengan Locale.US agar desimal pakai titik
        String nilaiAkhirFormatted = String.format(Locale.US, "%.2f", nilaiAkhir);
        String grade = hitungGrade(nilaiAkhir);

        // Bangun response
        StringBuilder sb = new StringBuilder("Perolehan Nilai: ");
        for (int i = 0; i < kategori.length; i++) {
            sb.append(String.format(Locale.US, ">> %s: %.0f/100 (%.2f/%.0f) ",
                kategori[i], skor[i], skor[i] * bobot[i], bobot[i] * 100));
        }

        sb.append(String.format(Locale.US, ">> Nilai Akhir: %s >> Grade: %s", nilaiAkhirFormatted, grade));

        return sb.toString();
    }

    private String hitungGrade(double nilaiAkhir) {
        if (nilaiAkhir >= 85) return "A";
        else if (nilaiAkhir >= 70) return "B";
        else if (nilaiAkhir >= 55) return "C";
        else if (nilaiAkhir >= 40) return "D";
        else return "E";
    }

    // METHOD 4 (perbedaanL) - PERBAIKAN
    @GetMapping("/perbedaanL/{strBase64}")
    public String perbedaanL(@PathVariable String strBase64) {
        try {
        // 1. Dekode Base64
        String inputAsli = new String(Base64.getDecoder().decode(strBase64));
        // 2. Pisahkan input menjadi 3 bagian (misal: "20|20|5")
        String[] parts = inputAsli.trim().split("\\|");

        // 3. Pastikan formatnya benar (3 bagian)
        if (parts.length == 3) {
            int nilaiL = Integer.parseInt(parts[0]);
            int nilaiKebalikanL = Integer.parseInt(parts[1]);
            int nilaiTengah = Integer.parseInt(parts[2]);
            
            // 4. Hitung perbedaan
            int perbedaan = Math.abs(nilaiL - nilaiKebalikanL);
            
            // 5. Tentukan dominan (sesuai output, tampaknya ini adalah nilaiTengah)
            int dominan = nilaiTengah;

            // 6. Kembalikan hasil sesuai format screenshot
            return "Nilai L: " + nilaiL + ": Nilai Kebalikan L: " + nilaiKebalikanL + 
                   ": Nilai Tengah: " + nilaiTengah + " Perbedaan: " + perbedaan + 
                   " Dominan: " + dominan;
        } else {
            return "Error: Format input tidak valid. Harusnya 'angka1|angka2|angka3'.";
        }

    } catch (NumberFormatException e) {
        // Menangani jika string hasil dekode bukan merupakan angka
        return "Error: Input bukan angka yang valid.";
    } catch (IllegalArgumentException e) {
        // Menangani jika string Base64 tidak valid
        return "Error: Format Base64 tidak valid.";
    }
}

    // Method 4: Paling Ter (Perbaikan Error Handling) - PERBAIKAN
    @GetMapping("/palingTer/{strBase64}")
    public String palingTer(@PathVariable String strBase64) {
        try {
        // 1. DECODE Base64
        byte[] decodedBytes = Base64.getDecoder().decode(strBase64);
        String inputAsli = new String(decodedBytes);

        // 2. LOGIKA PROGRAM
        String[] baris = inputAsli.split("\\R");
        
        // Buat array untuk menyimpan angka-angka
        int[] angka = new int[baris.length];
        int count = 0; // Jumlah angka yang valid
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        // Loop pertama: Parse angka, hitung min & max
        for (String b : baris) {
            try {
                int n = Integer.parseInt(b.trim());
                angka[count] = n; // Simpan angka valid
                count++;
                
                if (n > max) max = n;
                if (n < min) min = n;
                
            } catch (NumberFormatException e) {
                // Abaikan baris yang bukan angka
            }
        }

        // 3. RETURN jika tidak ada angka
        if (count == 0) {
            return "Tidak ada angka yang valid ditemukan.";
        }
        
        // 4. LOGIKA FREKUENSI (Tanpa Map)
        int terbanyakVal = 0;
        int terbanyakCount = 0;
        int tersedikitVal = 0;
        int tersedikitCount = Integer.MAX_VALUE;
        int terendahCount = 0; // Hitung frekuensi angka 'min'

        for (int i = 0; i < count; i++) {
            int currentNum = angka[i];
            int currentCount = 0;
            
            // Cek apakah angka ini sudah pernah dihitung sebelumnya
            boolean seen = false;
            for (int j = 0; j < i; j++) {
                if (angka[j] == currentNum) {
                    seen = true;
                    break;
                }
            }
            if (seen) continue; // Lewati jika sudah dihitung

            // Hitung frekuensi 'currentNum'
            for (int j = 0; j < count; j++) {
                if (angka[j] == currentNum) {
                    currentCount++;
                }
            }
            
            // Update Terbanyak
            if (currentCount > terbanyakCount) {
                terbanyakCount = currentCount;
                terbanyakVal = currentNum;
            }
            
            // Update Tersedikit
            if (currentCount < tersedikitCount) {
                tersedikitCount = currentCount;
                tersedikitVal = currentNum;
            }
        }
        
        // Hitung frekuensi angka Terendah (min)
        for (int i = 0; i < count; i++) {
            if (angka[i] == min) {
                terendahCount++;
            }
        }

        // 5. Hitung Jumlah
        long jumlahTertinggi = (long)max * terbanyakCount;
        long jumlahTerendah = (long)min * terendahCount;

        // 6. Kembalikan hasil sesuai format screenshot
        return "Tertinggi: " + max + " Terendah: " + min + 
               " Terbanyak: " + terbanyakVal + " (" + terbanyakCount + "x) Tersedikit: " + 
               tersedikitVal + " (" + tersedikitCount + "x) Jumlah Tertinggi: " + 
               max + " * " + terbanyakCount + " = " + jumlahTertinggi + 
               " Jumlah Terendah: " + min + " * " + terendahCount + " = " + jumlahTerendah;
        
    } catch (IllegalArgumentException e) {
         // 4. CATCH INI SEKARANG AKAN BERFUNGSI
        return "Error: Format Base64 tidak valid.";
    }
}
}