package org.delcom.starter.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Controller utama untuk aplikasi Spring Boot.
 * Menangani rute-rute dasar serta adaptasi studi kasus.
 */
@RestController
public class HomeController {

    // --- Konstanta Batas Nilai ---
    private static final double THRESHOLD_A = 79.5;
    private static final double THRESHOLD_AB = 72.0;
    private static final double THRESHOLD_B = 64.5;
    private static final double THRESHOLD_BC = 57.0;
    private static final double THRESHOLD_C = 49.5;
    private static final double THRESHOLD_D = 34.0;

    /**
     * Data program studi (prefix → nama prodi)
     */
    private static final Map<String, String> STUDY_PROGRAMS = Map.ofEntries(
            Map.entry("11S", "Sarjana Informatika"),
            Map.entry("12S", "Sarjana Sistem Informasi"),
            Map.entry("14S", "Sarjana Teknik Elektro"),
            Map.entry("21S", "Sarjana Manajemen Rekayasa"),
            Map.entry("22S", "Sarjana Teknik Metalurgi"),
            Map.entry("31S", "Sarjana Teknik Bioproses"),
            Map.entry("114", "Diploma 4 Teknologi Rekasaya Perangkat Lunak"),
            Map.entry("113", "Diploma 3 Teknologi Informasi"),
            Map.entry("133", "Diploma 3 Teknologi Komputer")
    );

    // --- Endpoint Tetap (JANGAN DIUBAH) ---
    @GetMapping("/")
    public String hello() {
        return "Hay Abdullah, selamat datang di pengembangan aplikasi dengan Spring Boot!";
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // --- Endpoint Studi Kasus ---

    @GetMapping("/informasiNim/{nim}")
    public ResponseEntity<String> informasiNim(@PathVariable String nim) {
        try {
            String result = handleNimInformation(nim);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/perolehanNilai/{encodedData}")
    public ResponseEntity<String> perolehanNilai(@PathVariable String encodedData) {
        try {
            String decoded = decodeBase64String(encodedData);
            String result = handleGradeCalculation(decoded);
            return ResponseEntity.ok(result);
        } catch (NoSuchElementException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            return new ResponseEntity<>("Format data input tidak valid atau tidak lengkap. Pastikan angka dan format sudah benar.", HttpStatus.BAD_REQUEST);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Input Base64 tidak valid.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/perbedaanL/{encodedMatrix}")
    public ResponseEntity<String> perbedaanL(@PathVariable String encodedMatrix) {
        try {
            String decoded = decodeBase64String(encodedMatrix);
            String result = handleMatrixDifference(decoded);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Input Base64 tidak valid.", HttpStatus.BAD_REQUEST);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("Format data matriks tidak valid atau tidak lengkap.", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/palingTer/{encodedNumbers}")
    public ResponseEntity<String> palingTer(@PathVariable String encodedNumbers) {
        try {
            String decoded = decodeBase64String(encodedNumbers);
            String result = handleFrequencyAnalysis(decoded);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Input Base64 tidak valid.", HttpStatus.BAD_REQUEST);
        }
    }

    // --- Utility & Helper Methods ---

    private String decodeBase64String(String encoded) {
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(encoded);
            return new String(decodedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalArgumentException("Input Base64 tidak valid: " + e.getMessage());
        }
    }

    private String determineGrade(double score) {
        if (score >= THRESHOLD_A) return "A";
        else if (score >= THRESHOLD_AB) return "AB";
        else if (score >= THRESHOLD_B) return "B";
        else if (score >= THRESHOLD_BC) return "BC";
        else if (score >= THRESHOLD_C) return "C";
        else if (score >= THRESHOLD_D) return "D";
        else return "E";
    }

    // --- Business Logic (Service Layer) ---

    private String handleNimInformation(String nim) {
        StringBuilder result = new StringBuilder();

        if (nim.length() != 8) {
            throw new IllegalArgumentException("Format NIM tidak valid. Harap masukkan 8 digit.");
        }

        String prefix = nim.substring(0, 3);
        String angkatanStr = nim.substring(3, 5);
        String nomorUrut = nim.substring(5);
        String prodi = STUDY_PROGRAMS.get(prefix);

        if (prodi != null) {
            int tahun = 2000 + Integer.parseInt(angkatanStr);
            result.append("Inforamsi NIM ").append(nim).append(": \n");
            result.append(">> Program Studi: ").append(prodi).append("\n");
            result.append(">> Angkatan: ").append(tahun).append("\n");
            result.append(">> Urutan: ").append(Integer.parseInt(nomorUrut));
        } else {
            throw new IllegalArgumentException("Prefix NIM '" + prefix + "' tidak ditemukan.");
        }
        return result.toString();
    }

    private String handleGradeCalculation(String input) {
        StringBuilder result = new StringBuilder();
        try (Scanner sc = new Scanner(input)) {
            sc.useLocale(Locale.US);

            int wPart = sc.nextInt();
            int wTask = sc.nextInt();
            int wQuiz = sc.nextInt();
            int wProj = sc.nextInt();
            int wMid = sc.nextInt();
            int wFinal = sc.nextInt();
            sc.nextLine();

            int totalPart = 0, maxPart = 0;
            int totalTask = 0, maxTask = 0;
            int totalQuiz = 0, maxQuiz = 0;
            int totalProj = 0, maxProj = 0;
            int totalMid = 0, maxMid = 0;
            int totalFinal = 0, maxFinal = 0;

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.equals("---")) break;

                String[] parts = line.split("\\|");
                String type = parts[0];
                int max = Integer.parseInt(parts[1]);
                int val = Integer.parseInt(parts[2]);

                switch (type) {
                    case "PA": maxPart += max; totalPart += val; break;
                    case "T": maxTask += max; totalTask += val; break;
                    case "K": maxQuiz += max; totalQuiz += val; break;
                    case "P": maxProj += max; totalProj += val; break;
                    case "UTS": maxMid += max; totalMid += val; break;
                    case "UAS": maxFinal += max; totalFinal += val; break;
                    default: break;
                }
            }

            double avgPart = (maxPart == 0) ? 0 : (totalPart * 100.0 / maxPart);
            double avgTask = (maxTask == 0) ? 0 : (totalTask * 100.0 / maxTask);
            double avgQuiz = (maxQuiz == 0) ? 0 : (totalQuiz * 100.0 / maxQuiz);
            double avgProj = (maxProj == 0) ? 0 : (totalProj * 100.0 / maxProj);
            double avgMid = (maxMid == 0) ? 0 : (totalMid * 100.0 / maxMid);
            double avgFinal = (maxFinal == 0) ? 0 : (totalFinal * 100.0 / maxFinal);

            int rPart = (int) Math.round(avgPart);
            int rTask = (int) Math.round(avgTask);
            int rQuiz = (int) Math.round(avgQuiz);
            int rProj = (int) Math.round(avgProj);
            int rMid = (int) Math.round(avgMid);
            int rFinal = (int) Math.round(avgFinal);

            double wAvgPart = (rPart / 100.0) * wPart;
            double wAvgTask = (rTask / 100.0) * wTask;
            double wAvgQuiz = (rQuiz / 100.0) * wQuiz;
            double wAvgProj = (rProj / 100.0) * wProj;
            double wAvgMid = (rMid / 100.0) * wMid;
            double wAvgFinal = (rFinal / 100.0) * wFinal;

            double finalScore = wAvgPart + wAvgTask + wAvgQuiz + wAvgProj + wAvgMid + wAvgFinal;

            result.append("Perolehan Nilai:\n");
            result.append(String.format(Locale.US, ">> Partisipatif: %d/100 (%.2f/%d)\n", rPart, wAvgPart, wPart));
            result.append(String.format(Locale.US, ">> Tugas: %d/100 (%.2f/%d)\n", rTask, wAvgTask, wTask));
            result.append(String.format(Locale.US, ">> Kuis: %d/100 (%.2f/%d)\n", rQuiz, wAvgQuiz, wQuiz));
            result.append(String.format(Locale.US, ">> Proyek: %d/100 (%.2f/%d)\n", rProj, wAvgProj, wProj));
            result.append(String.format(Locale.US, ">> UTS: %d/100 (%.2f/%d)\n", rMid, wAvgMid, wMid));
            result.append(String.format(Locale.US, ">> UAS: %d/100 (%.2f/%d)\n", rFinal, wAvgFinal, wFinal));
            result.append("\n");
            result.append(String.format(Locale.US, ">> Nilai Akhir: %.2f\n", finalScore));
            result.append(String.format(Locale.US, ">> Grade: %s\n", determineGrade(finalScore)));
        }
        return result.toString().trim();
    }

    private String handleMatrixDifference(String input) {
        StringBuilder result = new StringBuilder();
        try (Scanner sc = new Scanner(input)) {
            int size = sc.nextInt();
            int[][] matrix = new int[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    matrix[i][j] = sc.nextInt();
                }
            }

            if (size == 1) {
                int center = matrix[0][0];
                result.append("Nilai L: Tidak Ada\n");
                result.append("Nilai Kebalikan L: Tidak Ada\n");
                result.append("Nilai Tengah: ").append(center).append("\n");
                result.append("Perbedaan: Tidak Ada\n");
                result.append("Dominan: ").append(center);
                return result.toString();
            }

            if (size == 2) {
                int sum = Arrays.stream(matrix).flatMapToInt(Arrays::stream).sum();
                result.append("Nilai L: Tidak Ada\n");
                result.append("Nilai Kebalikan L: Tidak Ada\n");
                result.append("Nilai Tengah: ").append(sum).append("\n");
                result.append("Perbedaan: Tidak Ada\n");
                result.append("Dominan: ").append(sum);
                return result.toString();
            }

            int lSum = 0;
            for (int i = 0; i < size; i++) lSum += matrix[i][0];
            for (int j = 1; j < size - 1; j++) lSum += matrix[size - 1][j];

            int reverseSum = 0;
            for (int i = 0; i < size; i++) reverseSum += matrix[i][size - 1];
            for (int j = 1; j < size - 1; j++) reverseSum += matrix[0][j];

            int centerValue = (size % 2 == 1)
                    ? matrix[size / 2][size / 2]
                    : matrix[size / 2 - 1][size / 2 - 1] + matrix[size / 2 - 1][size / 2]
                    + matrix[size / 2][size / 2 - 1] + matrix[size / 2][size / 2];

            int difference = Math.abs(lSum - reverseSum);
            int dominant = (difference == 0) ? centerValue : Math.max(lSum, reverseSum);

            result.append("Nilai L: ").append(lSum).append(":\n");
            result.append("Nilai Kebalikan L: ").append(reverseSum).append("\n");
            result.append("Nilai Tengah: ").append(centerValue).append("\n");
            result.append("Perbedaan: ").append(difference).append("\n");
            result.append("Dominan: ").append(dominant);
        }
        return result.toString().trim();
    }

    private String handleFrequencyAnalysis(String input) {
        StringBuilder result = new StringBuilder();
        try (Scanner sc = new Scanner(input)) {
            List<Integer> nums = new ArrayList<>();
            while (sc.hasNextInt()) nums.add(sc.nextInt());

            if (nums.isEmpty()) {
                return "Tidak ada input";
            }

            Map<Integer, Integer> freq = new LinkedHashMap<>();
            int maxVal = Integer.MIN_VALUE, minVal = Integer.MAX_VALUE;
            int mostVal = 0, mostCount = 0;

            for (int n : nums) {
                freq.put(n, freq.getOrDefault(n, 0) + 1);
                int count = freq.get(n);
                if (count > mostCount) { mostCount = count; mostVal = n; }
                if (n > maxVal) maxVal = n;
                if (n < minVal) minVal = n;
            }

            Set<Integer> eliminated = new HashSet<>();
            int least = -1;
            for (int i = 0; i < nums.size(); ) {
                int curr = nums.get(i);
                if (eliminated.contains(curr)) { i++; continue; }
                int j = i + 1;
                while (j < nums.size() && nums.get(j) != curr) j++;
                if (j < nums.size()) {
                    for (int k = i + 1; k < j; k++) eliminated.add(nums.get(k));
                    eliminated.add(curr);
                    i = j + 1;
                } else {
                    least = curr; break;
                }
            }

            if (least == -1) return "Tidak ada angka unik";

            int topVal = -1, topCount = -1; long topProd = Long.MIN_VALUE;
            for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
                long prod = (long) e.getKey() * e.getValue();
                if (prod > topProd || (prod == topProd && e.getKey() > topVal)) {
                    topProd = prod; topVal = e.getKey(); topCount = e.getValue();
                }
            }

            int lowVal = minVal;
            int lowCount = freq.get(minVal);
            long lowProd = (long) lowVal * lowCount;

            result.append("Tertinggi: ").append(maxVal).append("\n");
            result.append("Terendah: ").append(minVal).append("\n");
            result.append("Terbanyak: ").append(mostVal).append(" (").append(mostCount).append("x)\n");
            result.append("Tersedikit: ").append(least).append(" (").append(freq.get(least)).append("x)\n");
            result.append("Jumlah Tertinggi: ").append(topVal).append(" * ").append(topCount).append(" = ").append(topProd).append("\n");
            result.append("Jumlah Terendah: ").append(lowVal).append(" * ").append(lowCount).append(" = ").append(lowProd);
        }
        return result.toString().trim();
    }
}
