package com.progym.utils;

/**
 * Class represents
 * simple calculator which helps convert
 * 
 * @author Karpachev Ihor
 */
public class FoodCalculator {

     /**
      * Calculate amount of callories, depends on product weight
      * 
      * @param kkal_In_100G
      * @param weight
      * @return
      */
     public static int getKkal(double kkal_In_100G, double weight) {
          return (int) (kkal_In_100G / 100 * weight);
     }

     /**
      * @param fat_In_100G
      * @param weight
      * @return
      */
     public static double getFat(double fat_In_100G, double weight) {
          return (fat_In_100G / 100 * weight);
     }

     /**
      * @param protein_In_100G
      * @param weight
      * @return
      */
     public static double getProtein(double protein_In_100G, double weight) {
          return (protein_In_100G / 100 * weight);
     }

     /**
      * @param carbs_In_100G
      * @param weight
      * @return
      */
     public static double getCarbs(double carbs_In_100G, double weight) {
          return (carbs_In_100G / 100 * weight);
     }

}
