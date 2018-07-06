package ChooseLover;

class Dancer {
	int sex;
	int glamourValue;
	Dancer parter;

	public Dancer(int sex, int glamourValue) {
		this.sex = sex;
		this.glamourValue = glamourValue;
		this.parter = null;
	}

}

public class ChooseDancer {

	Dancer[] women = new Dancer[10];
	Dancer[] men = new Dancer[12];

	public void createDancer() {
		for (int i = 1; i < 11; i++) {
			women[i - 1] = new Dancer(0, i);
			men[i - 1] = new Dancer(1, i);
		}
		men[10] = new Dancer(1, 11);
		men[11] = new Dancer(1, 12);
	}

	public void chooseParter() {
		for (int i = 0; i < 12; i++) {
			int chooseNum;
			Dancer myParter = null;
			if (men[i].parter == null) {
				chooseNum = (int) (Math.random() * 10);
				// System.out.println(chooseNum);
				myParter = women[chooseNum];
				if (myParter.parter == null) {
					men[i].parter = myParter;
					myParter.parter = men[i];
				} else if (myParter.parter.glamourValue < men[i].glamourValue) {
					myParter.parter.parter = null;
					men[i].parter = myParter;
					myParter.parter = men[i];
				}
			} else {
				chooseNum = (int) (Math.random() * 10);
				Dancer maybeParter = women[chooseNum];
				if (maybeParter.glamourValue > men[i].parter.glamourValue) {
					if (maybeParter.parter == null) {
						men[i].parter.parter = null;
						men[i].parter = maybeParter;
						maybeParter.parter = men[i];
					} else if (men[i].glamourValue > maybeParter.parter.glamourValue) {
						men[i].parter.parter = null;
						maybeParter.parter.parter = null;
						men[i].parter = maybeParter;
						maybeParter.parter = men[i];
					}
				}
			}
		}
		System.out.println("man  woman");
		for (int i = 0; i < 12; i++) {
			if (men[i].parter != null)
				System.out.println(" " + men[i].glamourValue + "    " + men[i].parter.glamourValue);
			else
				System.out.println(" " + men[i].glamourValue);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChooseDancer chooseDancer = new ChooseDancer();
		chooseDancer.createDancer();
		for (int i = 0; i < 100; i++) {
			System.out.println("第"+i+"轮选择：");
			chooseDancer.chooseParter();
		}

	}

}
