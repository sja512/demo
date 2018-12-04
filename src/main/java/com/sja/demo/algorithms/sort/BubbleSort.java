package com.sja.demo.algorithms.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 *
 * @author sja  created on 2018/12/4.
 */
public class BubbleSort {
    public static void main(String[] args) {
        //int[] array = new int[]{3,2,9,1,5,4,8,7};
        int[] array = new int[]{4, 3, 2, 1, 5, 6, 7, 8};
        sort2(array);
        System.out.println(Arrays.toString(array));
    }

    /**
     * 冒泡排序 相邻的元素两两比较，根据大小交换位置，从小到大排序
     *
     * @param array
     */
    private static void sort(int array[]) {
        int temp = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    /**
     * 优化 如果已是有序 没有必要比较下一轮大循环
     * int[] array = new int[]{9, 1, 2, 3, 4, 5, 6, 7, 8};
     * @param array
     */
    private static void sort1(int array[]) {
        int temp = 0;
        for (int i = 0; i < array.length; i++) {
            boolean isSort = true;
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    //有交换 数组无序
                    isSort = false;
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
            //有序直接跳出
            if (isSort) {
                break;
            }
        }
    }

    /**
     * 优化 如果已是有序 没有必要比较下一轮大循环
     * 增加无序的边界，边界之后的不需要比较
     * int[] array = new int[]{4, 3, 2, 1, 5, 6, 7, 8};
     * @param array
     */
    private static void sort2(int array[]) {
        int temp = 0;
        int lastExchangeIndex = 0;
        //无序数组的边界，每次比较到此位置
        int sortBorder = array.length - 1;
        for (int i = 0; i < array.length; i++) {
            boolean isSort = true;
            for (int j = 0; j < sortBorder; j++) {
                if (array[j] > array[j + 1]) {
                    //有交换 数组无序
                    isSort = false;
                    temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    lastExchangeIndex = j;
                }
            }
            //无序边界更新为最后一次交换的位置
            sortBorder = lastExchangeIndex;
            //有序直接跳出
            if (isSort) {
                break;
            }
        }
    }
}
