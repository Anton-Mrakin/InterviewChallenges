package com.mrakin.structures.two_pointer_array;
import java.util.Arrays;

public class RemoveDuplicates {
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
