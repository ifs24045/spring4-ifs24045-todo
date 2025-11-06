package org.delcom.starter.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Base64;

class HomeControllerTest {

    private HomeController controller;

    // Fungsi bantu encode string ke Base64
    private String encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    // @BeforeEach memastikan controller baru dibuat sebelum SETIAP tes
    @BeforeEach
    void setUp() {
        controller = new HomeController();
    }

    @Test
    @DisplayName("Mengembalikan pesan selamat datang yang benar")
    void hello_ShouldReturnWelcomeMessage() {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.hello();

        // Assert
        assertEquals("Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!", result);
    }

    // Tambahan test untuk metode sayHello dengan parameter nama
    @Test
    @DisplayName("Mengembalikan pesan sapaan yang dipersonalisasi")
    void helloWithName_ShouldReturnPersonalizedGreeting() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.sayHello("Abdullah");

        // Assert
        assertEquals("Hello, Abdullah!", result);
    }

    // Test untuk method informasiNim()
    // ========================================
    //       case "11S": jurusan = "Sarjana Informatika"; break;

    // Test untuk informasiNIM Sarjana Informatika
     @Test
    @DisplayName("Mengembalikan informasi nim Sarjana Informatika")
    void informasiNim_mengembalikanInformasiNimS1IF() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("11S24045");

        // Assert
        assertEquals("Informasi NIM 11S24045: >> Program Studi: Sarjana Informatika >> Angkatan: 2024 >> Urutan: 45", result);
    }

     @Test
    @DisplayName("Mengembalikan informasi nim Sarjana Sistem Informasi")
    void informasiNim_mengembalikanInformasiNimS1SI() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("12S24045");

        // Assert
        assertEquals("Informasi NIM 12S24045: >> Program Studi: Sarjana Sistem Informasi >> Angkatan: 2024 >> Urutan: 45", result);
    }
    
    @Test
    @DisplayName("Mengembalikan informasi nim Sarjana Teknik Elektro")
    void informasiNim_mengembalikanInformasiNimS1TE() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("14S24045");

        // Assert
        assertEquals("Informasi NIM 14S24045: >> Program Studi: Sarjana Teknik Elektro >> Angkatan: 2024 >> Urutan: 45", result);
    }

     @Test
    @DisplayName("Mengembalikan informasi nim Manajemen Rekayasa")
    void informasiNim_mengembalikanInformasiNimS1MR() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("21S24045");

        // Assert
        assertEquals("Informasi NIM 21S24045: >> Program Studi: Sarjana Manajemen Rekayasa >> Angkatan: 2024 >> Urutan: 45", result);
    }

    @Test
    @DisplayName("Mengembalikan informasi nim Teknik Metalurgi")
    void informasiNim_mengembalikanInformasiNimS1TM() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("22S24045");

        // Assert
        assertEquals("Informasi NIM 22S24045: >> Program Studi: Sarjana Teknik Metalurgi >> Angkatan: 2024 >> Urutan: 45", result);
    }

    @Test
    @DisplayName("Mengembalikan informasi nim Sarjana Teknik Bioproses")
    void informasiNim_mengembalikanInformasiNimS1TB() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("31S24045");

        // Assert
        assertEquals("Informasi NIM 31S24045: >> Program Studi: Sarjana Teknik Bioproses >> Angkatan: 2024 >> Urutan: 45", result);
    }

    @Test
    @DisplayName("Mengembalikan informasi nim Diploma 4 Teknologi Rekayasa Perangkat Lunak")
    void informasiNim_mengembalikanInformasiNimD4TRPL() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("11424045");

        // Assert
        assertEquals("Informasi NIM 11424045: >> Program Studi: Diploma 4 Teknologi Rekayasa Perangkat Lunak >> Angkatan: 2024 >> Urutan: 45", result);
    } 

    @Test
    @DisplayName("Mengembalikan informasi nim Diploma 3 Teknologi Informasi")
    void informasiNim_mengembalikanInformasiNimD3TI() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("11324045");

        // Assert
        assertEquals("Informasi NIM 11324045: >> Program Studi: Diploma 3 Teknologi Informasi >> Angkatan: 2024 >> Urutan: 45", result);
    } 

    @Test
    @DisplayName("Mengembalikan informasi nim Diploma 3 Teknologi Komputer")
    void informasiNim_mengembalikanInformasiNimD3TK() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("13324045");

        // Assert
        assertEquals("Informasi NIM 13324045: >> Program Studi: Diploma 3 Teknologi Komputer >> Angkatan: 2024 >> Urutan: 45", result);
    } 

    @Test
    @DisplayName("Mengembalikan informasi nim NIM tidak sesuai format")
    void informasiNim_mengembalikanInformasiNimXX() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("1xx24045");

        // Assert
        assertEquals("Prefix 1xx tidak dikenali", result);
    } 

    @Test
    @DisplayName("Mengembalikan informasi nim NIM tidak sesuai format")
    void informasiNim_mengembalikanInformasiNim4() throws Exception {
        // Arrange
        HomeController controller = new HomeController();

        // Act
        String result = controller.informasiNim("12S24");

        // Assert
        assertEquals("NIM tidak sesuai format!", result);
 
    }
   
@Test
    @DisplayName("Perolehan nilai - Menghitung data untuk Grade A")
    void testPerolehanNilai_DataLengkap_GradeA() {
        String input = "Partisipatif|100\nTugas|100\nKuis|80\nProyek|90\nUTS|85\nUAS|85";
        String base64Input = encode(input);

        String result = controller.perolehanNilai(base64Input);

        assertTrue(result.contains("Nilai Akhir: 89.00"));
        assertTrue(result.contains("Grade: A"));
    }

    @Test
    @DisplayName("Perolehan nilai - Menghitung data lengkap (Grade B)")
    void testPerolehanNilai_DataLengkap_GradeB() {
        String input = "Partisipatif|80\nTugas|90\nKuis|85\nProyek|70\nUTS|75\nUAS|88";
        String base64Input = encode(input);

        String result = controller.perolehanNilai(base64Input);

        assertTrue(result.contains("Nilai Akhir: 81.90"));
        assertTrue(result.contains("Grade: B"));
    }

    @Test
    @DisplayName("Perolehan nilai - Menghitung data untuk Grade C")
    void testPerolehanNilai_GradeC() {
        String input = "Partisipatif|60\nTugas|60\nKuis|60\nProyek|60\nUTS|60\nUAS|60";
        String base64Input = encode(input);

        String result = controller.perolehanNilai(base64Input);

        assertTrue(result.contains("Nilai Akhir: 60.00"));
        assertTrue(result.contains("Grade: C"));
    }

    @Test
    @DisplayName("Perolehan nilai - Menghitung data untuk Grade D")
    void testPerolehanNilai_GradeD() {
        String input = "Partisipatif|100\nTugas|100\nUTS|40\nUAS|40"; // Hanya kategori tertentu
        String base64Input = encode(input);

        String result = controller.perolehanNilai(base64Input);

        assertTrue(result.contains("Nilai Akhir: 45.00"));
        assertTrue(result.contains("Grade: D"));
    }

    @Test
    @DisplayName("Perolehan nilai - Data tidak lengkap (Grade E)")
    void testPerolehanNilai_DataTidakLengkap_GradeE() {
        String input = "UTS|70\nUAS|60"; // Hanya 2 kategori
        String base64Input = encode(input);

        String result = controller.perolehanNilai(base64Input);

        assertTrue(result.contains("Nilai Akhir: 32.00"));
        assertTrue(result.contains("Grade: E"));
    }

    @Test
    @DisplayName("Perolehan nilai - Memicu NumberFormatException (catch merah)")
    void testPerolehanNilai_NumberFormatException() {
        String input = "Partisipatif|100\nTugas|abc\nUAS|50"; // Tugas tidak valid
        String base64Input = encode(input);

        String result = controller.perolehanNilai(base64Input);

        assertTrue(result.contains("Nilai Akhir: 25.00"));
    }

    @Test
    @DisplayName("Perolehan nilai - Base64 tidak valid")
    void testPerolehanNilai_Base64TidakValid() {
        String invalidBase64 = "MTIz*"; // Karakter tidak valid di Base64

        String result = controller.perolehanNilai(invalidBase64);

        assertTrue(result.contains("Nilai Akhir: 0.00"));
        assertTrue(result.contains("Grade: E"));
    }

    @Test
    @DisplayName("Perolehan nilai - Mengabaikan kategori yang tidak dikenal (Perbaikan Kuning)")
    void testPerolehanNilai_InvalidKategoriName() {
        String input = "Partisipatif|100\nNilaiExtra|100\nUAS|50"; // NilaiExtra diabaikan
        String base64Input = encode(input);

        String result = controller.perolehanNilai(base64Input);

        assertTrue(result.contains("Nilai Akhir: 25.00"));
        assertTrue(result.contains("Grade: E"));
    }

    @Test
    @DisplayName("Perolehan nilai - Mengabaikan format baris yang salah (Perbaikan Kuning)")
    void testPerolehanNilai_InvalidLineFormat() {
        String input = "Partisipatif|100\nIni baris yang salah\nUAS|50|tambahan"; // Format tidak valid
        String base64Input = encode(input);

        String result = controller.perolehanNilai(base64Input);

        assertTrue(result.contains("Nilai Akhir: 10.00"));
        assertTrue(result.contains("Grade: E"));
    }

    // Test untuk metode perbedaanL()
    
    @Test
    @DisplayName("perbedaanL - Input valid sesuai screenshot")
    void testPerbedaanL_ValidInput() {
        String input = "20|20|5";
        String base64Input = encode(input);
        String result = controller.perbedaanL(base64Input);
        
        // Memeriksa bagian-bagian dari output yang diharapkan
        assertTrue(result.contains("Nilai L: 20"));
        assertTrue(result.contains("Nilai Kebalikan L: 20"));
        assertTrue(result.contains("Nilai Tengah: 5"));
        assertTrue(result.contains("Perbedaan: 0"));
        assertTrue(result.contains("Dominan: 5"));
    }

    @Test
    @DisplayName("perbedaanL - Format input salah (bukan 3 bagian)")
    void testPerbedaanL_InvalidFormat() {
        String input = "20|20"; // Hanya 2 bagian
        String base64Input = encode(input);
        String result = controller.perbedaanL(base64Input);
        assertEquals("Error: Format input tidak valid. Harusnya 'angka1|angka2|angka3'.", result);
    }

    @Test
    @DisplayName("perbedaanL - Input bukan angka (NumberFormatException)")
    void testPerbedaanL_NotANumber() {
        String input = "20|abc|5"; // 'abc' bukan angka
        String base64Input = encode(input);
        String result = controller.perbedaanL(base64Input);
        assertEquals("Error: Input bukan angka yang valid.", result);
    }

    @Test
    @DisplayName("perbedaanL - Base64 tidak valid (IllegalArgumentException)")
    void testPerbedaanL_InvalidBase64() {
        String invalidBase64 = "MTIz*"; // Base64 rusak
        String result = controller.perbedaanL(invalidBase64);
        assertEquals("Error: Format Base64 tidak valid.", result);
    }


    @Test
    @DisplayName("Paling Ter - Input valid sesuai screenshot")
    void testPalingTer_ValidInput() {
        String input = """
            10
            5
            9
            10
            5
            10
            """;

        
        String base64Input = encode(input);
        String result = controller.palingTer(base64Input);
        
        String expected = "Tertinggi: 10 Terendah: 5 Terbanyak: 10 (3x) Tersedikit: 9 (1x) Jumlah Tertinggi: 10 * 3 = 30 Jumlah Terendah: 5 * 2 = 10";
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Paling Ter - Tidak ada angka valid")
    void testPalingTer_NoValidNumbers() {
        String input = "abc\ndef\nghi";
        String base64Input = encode(input);
        String result = controller.palingTer(base64Input);
        assertEquals("Tidak ada angka yang valid ditemukan.", result);
    }

    @Test
    @DisplayName("Paling Ter - Base64 tidak valid")
    void testPalingTer_InvalidBase64() {
        String invalidBase64 = "MTIz*"; // Base64 rusak
        String result = controller.palingTer(invalidBase64);
        assertEquals("Error: Format Base64 tidak valid.", result);
    }

    @Test
    @DisplayName("Paling Ter - Input dengan angka negatif")
    void testPalingTer_WithNegativeNumbers() {
        String input = """
            -10
            5
            -10
            -5
            5
            -10
            """;

        
        String base64Input = encode(input);
        String result = controller.palingTer(base64Input);
        
// BENAR:
String expected = "Tertinggi: 5 Terendah: -10 Terbanyak: -10 (3x) Tersedikit: -5 (1x) Jumlah Tertinggi: 5 * 3 = 15 Jumlah Terendah: -10 * 3 = -30";
    }

    @Test
    @DisplayName("Paling Ter - Input dengan frekuensi yang sama")
    void testPalingTer_SameFrequency() {
        String input = """
            10
            5
            10
            5
            """;
   
        
        String base64Input = encode(input);
        String result = controller.palingTer(base64Input);
        
        // Tes ini memaksa 'if (currentCount < tersedikitCount)' menjadi false
        String expected = "Tertinggi: 10 Terendah: 5 Terbanyak: 10 (2x) Tersedikit: 10 (2x) Jumlah Tertinggi: 10 * 2 = 20 Jumlah Terendah: 5 * 2 = 10";
        assertEquals(expected, result);
    }
}