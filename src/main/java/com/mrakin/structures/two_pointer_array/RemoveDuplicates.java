package com.mrakin.structures.two_pointer_array;
import java.util.Arrays;

public class RemoveDuplicates {
    /**
     * Удаляет дубликаты из отсортированного массива целых чисел на месте.
     * Возвращает новый массив, содержащий только уникальные элементы.
     *
     * @param nums отсортированный массив чисел
     * @return массив уникальных элементов
     */
    public int[] removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) {
            return new int[0];
        }

        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            // Если нашли новый уникальный элемент
            if (nums[fast] != nums[slow]) {
                slow++;
                nums[slow] = nums[fast]; // Переносим его в начало массива
            }
        }

        // Возвращаем копию массива до указателя slow включительно
        return Arrays.copyOf(nums, slow + 1);
    }
}
