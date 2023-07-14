import java.util.Random;

public class ergeshov_baistan_hw_4 {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {270, 280, 250, 300, 400, 250, 300, 200};
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 7, 4, 9};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        berserksAbility();
        chooseBossDefence();
        medicsAbility();
        bossHits();
        golemsAbility();
        luckiesAbility();
        heroesHit();
        thorsAbility();
        printStatistics();

    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] -= bossDamage; // => heroesHealth[i] = heroesHealth[i] - bossDamage;
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
                if (heroesHealth[6] < 0) {
                    heroesHealth[6] = 0;
                }
            }
        }
    }


    public static void berserksAbility() {
        int blockDamage = bossDamage / 5;
        heroesHealth[6] -= blockDamage;
        bossDamage += blockDamage;
        System.out.println(heroesAttackType[6] + " blocked " + blockDamage +
                " boss damage and returned it");
    }


    public static void golemsAbility() {
        int takenDamageFromBoss = 0;
        boolean onlyGetsOneShot = false;

        for (int x = 0; x < heroesHealth.length; x++) {
            if(heroesHealth[4] < 0){
                heroesHealth[4] = 0;
                if (heroesHealth[4] > 0 && bossHealth >= 0) {
                    if (heroesAttackType[x] != "Golem") {
                        if (!onlyGetsOneShot) {
                            onlyGetsOneShot = true;
                            takenDamageFromBoss = bossDamage % 5;
                            heroesHealth[4] -= takenDamageFromBoss;
                            System.out.println(heroesAttackType[4] + " took boss damage " +
                                    takenDamageFromBoss + " from hero " + heroesAttackType[x]);
                        }
                    }
                }
            }
        }
    }

    public static void thorsAbility() {
        Random random = new Random();
        boolean thor = random.nextBoolean();
        if (thor) {
            for (int i = 0; i < heroesDamage[i]; i++) {
                heroesHealth[i] += bossDamage;
            }
            System.out.println(heroesAttackType[7] + " stunned the boss!");
        }
    }

    public static void luckiesAbility(){
        Random escape = new Random();
        boolean randomEscape = escape.nextBoolean();
        if (randomEscape){
            if (heroesHealth[5]>0){
                heroesHealth[5] += bossDamage;
                System.out.println("Hero " + heroesAttackType[4] + " just escaped attack!!!");
            }
        }
    }

    public static void medicsAbility() {
        int healPoint = 100;
        boolean justOneHealInOneRound = false;

        for (int x = 0; x < heroesHealth.length; x++) {
            if (heroesHealth[3] > 0 && bossHealth >= 0) {
                if (heroesHealth[x] < 100 && heroesHealth[x] > 0) {
                    if (!justOneHealInOneRound) {
                        if (heroesAttackType[x] != "Medic") {
                            justOneHealInOneRound = true;
                            heroesHealth[x] += healPoint;
                            System.out.println(heroesAttackType[3] + " just healed: " + heroesAttackType[x]);
                        }
                    }
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                bossHealth -= damage; // => bossHealth = bossHealth - heroesDamage[i];
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
            }
        }
    }

    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] +
                    " damage: " + heroesDamage[i]);
        }
    }
}