package com.example.ms1.wolfcatcheggs.Model;

 public class ChangingVariables  {
    protected static long spawnNewEgg;
    protected static long animatorDiraction;

    public ChangingVariables(Byte z) {
            switch (z) {
                case 1:
                    spawnNewEgg = 5000;
                    animatorDiraction = 3000;
                    break;
                case 2:
                    spawnNewEgg = 3000;
                    animatorDiraction = 2500;
                    break;
                default:
                    spawnNewEgg = 1000;
                    animatorDiraction = 2000;
                    break;
            }
    }
}
