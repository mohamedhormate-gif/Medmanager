package medmanager;
import java.util.Scanner;

public class Main {

    // ── Constantes ──
    static final int MAX_PATIENTS = 100;
    static final int MAX_SERVICES = 5;

    // Patients
    static String[] nomsPatients = new String[MAX_PATIENTS];
    static String[] prenomsPatients = new String[MAX_PATIENTS];
    static int[] anneesNaissance = new int[MAX_PATIENTS];
    static int[] servicePatient = new int[MAX_PATIENTS];
    static int nbPatients = 0;

    // Services
    static String[] nomsServices = {"Urgences", "Cardiologie", "Pédiatrie", "Chirurgie", "Radiologie"};
    static int[] capacitesServices = {3, 2, 2, 2, 1};
    static int[] occupationServices = new int[MAX_SERVICES];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            afficherMenu();
            choix = lireChoix(scanner);

            switch (choix) {
                case 1 -> ajouterPatient(scanner);
                case 2 -> afficherPatients();
                case 3 -> rechercherPatient(scanner);
                case 4 -> afficherStatistiques();
                case 5 -> afficherPatientsTries();
                case 0 -> System.out.println("\n👋 Au revoir !");
                default -> System.out.println("⚠ Choix invalide.");
            }
        } while (choix != 0);

        scanner.close();
    }

    static void afficherMenu() {
        System.out.println("\n══════    MedManager    ══════");
        System.out.println("1. ➕ Ajouter un patient");
        System.out.println("2. 📋 Afficher tous les patients");
        System.out.println("3. 🔍 Rechercher un patient");
        System.out.println("4. 📊 Statistiques");
        System.out.println("5. 🔤 Afficher patients triés");
        System.out.println("0. 🚪 Quitter");
        System.out.print("Votre choix : ");
    }

    static int lireChoix(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("⚠ Entrez un nombre : ");
            scanner.next();
        }
        int choix = scanner.nextInt();
        scanner.nextLine();
        return choix;
    }

    // ✅ Exercice 1 + 4
    static void ajouterPatient(Scanner scanner) {

        if (nbPatients >= MAX_PATIENTS) {
            System.out.println("⚠ Capacité maximale atteinte !");
            return;
        }

        System.out.println("\n--- Nouveau Patient ---");

        System.out.print("Nom : ");
        nomsPatients[nbPatients] = scanner.nextLine();

        System.out.print("Prénom : ");
        prenomsPatients[nbPatients] = scanner.nextLine();

        int annee;
        do {
            System.out.print("Année de naissance : ");
            annee = lireChoix(scanner);
            int age = 2026 - annee;

            if (age < 0 || age > 150) {
                System.out.println("⚠ Âge invalide ! (0 - 150)");
            } else {
                break;
            }
        } while (true);

        anneesNaissance[nbPatients] = annee;

        // Services
        System.out.println("Choisir un service :");
        for (int i = 0; i < MAX_SERVICES; i++) {
            System.out.printf("%d. %s (%d/%d)\n", i + 1,
                    nomsServices[i],
                    occupationServices[i],
                    capacitesServices[i]);
        }

        int choixService;
        do {
            System.out.print("Service : ");
            choixService = lireChoix(scanner) - 1;

            if (choixService < 0 || choixService >= MAX_SERVICES) {
                System.out.println("⚠ Service invalide.");
            } else if (occupationServices[choixService] >= capacitesServices[choixService]) {
                System.out.println("⚠ Service complet !");
            } else {
                break;
            }
        } while (true);

        servicePatient[nbPatients] = choixService;
        occupationServices[choixService]++;
        nbPatients++;

        System.out.println("✅ Patient enregistré !");
    }

    // ✅ Exercice 5
    static void afficherPatients() {

        if (nbPatients == 0) {
            System.out.println("Aucun patient.");
            return;
        }

        System.out.println("┌─────┬────────────────┬────────────────┬───────┐");
        System.out.println("│  #  │ Nom            │ Prénom         │  Âge  │");
        System.out.println("├─────┼────────────────┼────────────────┼───────┤");

        for (int i = 0; i < nbPatients; i++) {
            int age = 2026 - anneesNaissance[i];
            System.out.printf("│ %-3d │ %-14s │ %-14s │ %-5d │\n",
                    i + 1,
                    nomsPatients[i],
                    prenomsPatients[i],
                    age);
        }

        System.out.println("└─────┴────────────────┴────────────────┴───────┘");
    }

    static void rechercherPatient(Scanner scanner) {
        System.out.print("Rechercher (nom) : ");
        String recherche = scanner.nextLine().toLowerCase();
        boolean trouve = false;

        for (int i = 0; i < nbPatients; i++) {
            if (nomsPatients[i].toLowerCase().contains(recherche)) {
                System.out.println(prenomsPatients[i] + " " + nomsPatients[i]);
                trouve = true;
            }
        }

        if (!trouve) System.out.println("Aucun résultat.");
    }

    // ✅ Exercice 2
    static void afficherStatistiques() {

        if (nbPatients == 0) {
            System.out.println("Aucune donnée.");
            return;
        }

        int totalAge = 0;
        int minAge = 999;
        int maxAge = 0;

        for (int i = 0; i < nbPatients; i++) {
            int age = 2026 - anneesNaissance[i];
            totalAge += age;
            if (age < minAge) minAge = age;
            if (age > maxAge) maxAge = age;
        }

        double moyenne = (double) totalAge / nbPatients;

        System.out.println("\n📊 Statistiques :");
        System.out.println("Total patients : " + nbPatients);
        System.out.println("Âge moyen : " + String.format("%.2f", moyenne));
        System.out.println("Plus jeune : " + minAge);
        System.out.println("Plus vieux : " + maxAge);
    }

    // ✅ Exercice 3 (Bubble Sort)
    static void afficherPatientsTries() {

        for (int i = 0; i < nbPatients - 1; i++) {
            for (int j = 0; j < nbPatients - i - 1; j++) {

                if (nomsPatients[j].compareToIgnoreCase(nomsPatients[j + 1]) > 0) {

                    // échange noms
                    String tempNom = nomsPatients[j];
                    nomsPatients[j] = nomsPatients[j + 1];
                    nomsPatients[j + 1] = tempNom;

                    // échange prénoms
                    String tempPrenom = prenomsPatients[j];
                    prenomsPatients[j] = prenomsPatients[j + 1];
                    prenomsPatients[j + 1] = tempPrenom;

                    // échange années
                    int tempAnnee = anneesNaissance[j];
                    anneesNaissance[j] = anneesNaissance[j + 1];
                    anneesNaissance[j + 1] = tempAnnee;

                    // échange services
                    int tempService = servicePatient[j];
                    servicePatient[j] = servicePatient[j + 1];
                    servicePatient[j + 1] = tempService;
                }
            }
        }

        afficherPatients();
    }
}