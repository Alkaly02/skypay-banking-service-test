# Banking Service Test

Un système bancaire simple implémentant les fonctionnalités de base : dépôt, retrait et génération de relevé de compte.

## 📋 Description

Ce projet est une implémentation d'un système bancaire minimal qui permet de :
- Effectuer des dépôts d'argent
- Effectuer des retraits d'argent
- Afficher un relevé bancaire avec l'historique des transactions

Le projet a été développé dans le cadre d'un test technique et respecte les spécifications suivantes :
- Utilisation d'une classe `Account` implémentant une interface publique
- Gestion des exceptions pour les entrées invalides
- Utilisation d'`ArrayList` pour stocker les transactions
- Utilisation d'`int` pour les montants (simplification pour le test)

## 🚀 Prérequis

- **Java** : Version 17 ou supérieure
- **Maven** : Version 3.6 ou supérieure

Pour vérifier vos installations :
```bash
java -version
mvn -version
```

## 📦 Installation

1. Clonez le repository :
```bash
git clone <repository-url>
cd banking-service-test-1
```

2. Compilez le projet :
```bash
mvn clean compile
```

## 🏃 Exécution

### Exécuter le programme principal

```bash
mvn compile
java -cp target/classes dev.lka.Main
```

**Sortie attendue :**
```
Date       || Amount || Balance
14-01-2012 || -500 || 2500
13-01-2012 || 2000 || 3000
10-01-2012 || 1000 || 1000
```

### Exécuter les tests

```bash
mvn test
```

Tous les tests unitaires (15 tests) devraient passer avec succès.

## 📖 Utilisation

### Exemple de code

```java
import dev.lka.service.Account;
import dev.lka.exception.CustomException;
import java.time.LocalDate;

public class Example {
    public static void main(String[] args) {
        // Créer un compte avec un solde initial de 0
        Account account = new Account(0);

        try {
            // Effectuer un dépôt
            account.deposit(1000, LocalDate.of(2012, 1, 10));

            // Effectuer un autre dépôt
            account.deposit(2000, LocalDate.of(2012, 1, 13));

            // Effectuer un retrait
            account.withdraw(500, LocalDate.of(2012, 1, 14));

            // Afficher le relevé
            account.printStatement();

        } catch (CustomException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
```

### API Principale

#### `Account`

**Constructeurs :**
- `Account()` - Crée un compte avec un solde initial de 0
- `Account(int initialBalance)` - Crée un compte avec un solde initial spécifié

**Méthodes :**
- `void deposit(int amount)` - Effectue un dépôt avec la date actuelle
- `void deposit(int amount, LocalDate date)` - Effectue un dépôt avec une date spécifique
- `void withdraw(int amount)` - Effectue un retrait avec la date actuelle
- `void withdraw(int amount, LocalDate date)` - Effectue un retrait avec une date spécifique
- `void printStatement()` - Affiche le relevé bancaire
- `int getBalance()` - Retourne le solde actuel
- `List<Transaction> getTransactions()` - Retourne une copie de la liste des transactions

## 🏗️ Architecture

```
src/main/java/dev/lka/
├── service/
│   ├── Account.java           # Classe principale implémentant la logique bancaire
│   └── AccountService.java    # Interface définissant le contrat
├── model/
│   ├── Transaction.java       # Record représentant une transaction
│   └── TransactionType.java   # Enum pour le type de transaction
├── exception/
│   └── CustomException.java    # Exception personnalisée
└── Main.java                  # Point d'entrée du programme

src/test/java/dev/lka/model/
└── AccountTest.java           # Tests unitaires
```

## 🧪 Tests

Le projet inclut une suite complète de tests unitaires couvrant :

- ✅ Scénario d'acceptation complet
- ✅ Opérations de dépôt et retrait
- ✅ Gestion des exceptions (montants invalides, solde insuffisant)
- ✅ Ordre chronologique des transactions
- ✅ Format de sortie du relevé
- ✅ Validation des entrées

**Exécuter les tests :**
```bash
mvn test
```

## ⚠️ Gestion des erreurs

Le projet lève des `CustomException` dans les cas suivants :

- **Dépôt invalide** : Montant ≤ 0
  ```java
  throw new CustomException("Deposit amount must be positive");
  ```

- **Retrait invalide** : Montant ≤ 0
  ```java
  throw new CustomException("Withdrawal amount must be positive");
  ```

- **Solde insuffisant** : Tentative de retrait supérieur au solde
  ```java
  throw new CustomException("Insufficient balance");
  ```

## 🔧 Technologies utilisées

- **Java 17** - Langage de programmation
- **Maven** - Gestion des dépendances et build
- **JUnit 5** - Framework de tests unitaires
- **Java Time API** - Gestion des dates (`LocalDate`, `DateTimeFormatter`)

## 📝 Concepts abordés

Ce projet illustre plusieurs concepts importants :

- **Programmation Orientée Objet** : Classes, interfaces, encapsulation
- **Collections Java** : `ArrayList`, gestion des listes
- **Gestion des exceptions** : Exceptions personnalisées, validation
- **API Date/Time** : Manipulation et formatage des dates
- **Surcharge de méthodes** : Méthodes avec et sans paramètres de date
- **Records Java** : Utilisation de records pour les données immuables
- **Tests unitaires** : Tests avec JUnit 5, capture de sortie console

## 📌 Notes importantes

- ⚠️ **Montants en `int`** : Ce projet utilise `int` pour simplifier le test. En production, il faudrait utiliser `BigDecimal` pour garantir la précision financière.

- 📅 **Format de date** : Les dates sont formatées selon le pattern `dd-MM-yyyy` (ex: `10-01-2012`).

- 💾 **Stockage en mémoire** : Les transactions sont stockées en mémoire dans un `ArrayList`. Elles ne sont pas persistées (pas de base de données).

- 🔒 **Encapsulation** : La méthode `getTransactions()` retourne une copie défensive de la liste pour protéger l'état interne.

## 📊 Exemple de sortie

```
Date       || Amount || Balance
14-01-2012 || -500 || 2500
13-01-2012 || 2000 || 3000
10-01-2012 || 1000 || 1000
```

## 🤝 Contribution

Ce projet a été développé dans le cadre d'un test technique. Pour toute question ou suggestion, n'hésitez pas à ouvrir une issue.

## 📄 Licence

Ce projet est un test technique et n'est pas destiné à un usage en production.

---

**Développé avec ❤️ en Java**
