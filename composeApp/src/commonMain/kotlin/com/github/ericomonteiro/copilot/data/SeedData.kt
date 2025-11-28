package com.github.ericomonteiro.copilot.data

import com.github.ericomonteiro.copilot.data.repository.ProblemRepository

suspend fun seedDatabase(repository: ProblemRepository) {
    val problems = listOf(
        Triple("LeetCode", "Two Sum", "Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target. You may assume that each input would have exactly one solution, and you may not use the same element twice."),
        Triple("LeetCode", "Reverse Linked List", "Given the head of a singly linked list, reverse the list, and return the reversed list."),
        Triple("LeetCode", "Valid Parentheses", "Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid. An input string is valid if: Open brackets must be closed by the same type of brackets. Open brackets must be closed in the correct order."),
        Triple("LeetCode", "Merge Two Sorted Lists", "You are given the heads of two sorted linked lists list1 and list2. Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists."),
        Triple("LeetCode", "Best Time to Buy and Sell Stock", "You are given an array prices where prices[i] is the price of a given stock on the ith day. You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock."),
        Triple("LeetCode", "Binary Search", "Given an array of integers nums which is sorted in ascending order, and an integer target, write a function to search target in nums. If target exists, then return its index. Otherwise, return -1."),
        Triple("LeetCode", "Maximum Subarray", "Given an integer array nums, find the subarray with the largest sum, and return its sum."),
        Triple("LeetCode", "Climbing Stairs", "You are climbing a staircase. It takes n steps to reach the top. Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?"),
        Triple("LeetCode", "Linked List Cycle", "Given head, the head of a linked list, determine if the linked list has a cycle in it."),
        Triple("LeetCode", "Palindrome Number", "Given an integer x, return true if x is a palindrome, and false otherwise."),
        Triple("HackerRank", "FizzBuzz", "Write a program that prints the numbers from 1 to n. But for multiples of three print Fizz instead of the number and for the multiples of five print Buzz. For numbers which are multiples of both three and five print FizzBuzz."),
        Triple("HackerRank", "Array Rotation", "Given an array of integers and a number, perform left rotations on the array. Return the updated array to be printed as a single line of space-separated integers."),
        Triple("LeetCode", "Valid Anagram", "Given two strings s and t, return true if t is an anagram of s, and false otherwise. An Anagram is a word or phrase formed by rearranging the letters of a different word or phrase."),
        Triple("LeetCode", "Contains Duplicate", "Given an integer array nums, return true if any value appears at least twice in the array, and return false if every element is distinct."),
        Triple("LeetCode", "Product of Array Except Self", "Given an integer array nums, return an array answer such that answer[i] is equal to the product of all the elements of nums except nums[i]."),
        Triple("LeetCode", "3Sum", "Given an integer array nums, return all the triplets [nums[i], nums[j], nums[k]] such that i != j, i != k, and j != k, and nums[i] + nums[j] + nums[k] == 0."),
        Triple("LeetCode", "Longest Substring Without Repeating Characters", "Given a string s, find the length of the longest substring without repeating characters."),
        Triple("LeetCode", "Container With Most Water", "You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]). Find two lines that together with the x-axis form a container, such that the container contains the most water."),
        Triple("LeetCode", "Merge Intervals", "Given an array of intervals where intervals[i] = [starti, endi], merge all overlapping intervals, and return an array of the non-overlapping intervals that cover all the intervals in the input."),
        Triple("LeetCode", "Group Anagrams", "Given an array of strings strs, group the anagrams together. You can return the answer in any order."),
        Triple("CodeSignal", "First Duplicate", "Given an array a that contains only numbers in the range from 1 to a.length, find the first duplicate number for which the second occurrence has the minimal index."),
        Triple("LeetCode", "Rotate Image", "You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise)."),
        Triple("LeetCode", "Spiral Matrix", "Given an m x n matrix, return all elements of the matrix in spiral order."),
        Triple("LeetCode", "Jump Game", "You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position. Return true if you can reach the last index, or false otherwise."),
        Triple("LeetCode", "Unique Paths", "There is a robot on an m x n grid. The robot is initially located at the top-left corner. The robot tries to move to the bottom-right corner. The robot can only move either down or right at any point in time. Given the two integers m and n, return the number of possible unique paths that the robot can take to reach the bottom-right corner."),
        Triple("LeetCode", "Coin Change", "You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money. Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1."),
        Triple("LeetCode", "Longest Common Subsequence", "Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0."),
        Triple("LeetCode", "Word Break", "Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words."),
        Triple("LeetCode", "House Robber", "You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night. Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police."),
        Triple("LeetCode", "Number of Islands", "Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands. An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.")
    )
    
    val difficulties = listOf("Easy", "Easy", "Easy", "Easy", "Easy", "Easy", "Medium", "Easy", "Easy", "Easy", 
                             "Easy", "Easy", "Easy", "Easy", "Medium", "Medium", "Medium", "Medium", "Medium", "Medium",
                             "Easy", "Medium", "Medium", "Medium", "Medium", "Medium", "Medium", "Medium", "Medium", "Medium")
    
    problems.forEachIndexed { index, (platform, name, description) ->
        repository.insertProblem(
            platform = platform,
            problemName = name,
            problemNumber = (index + 1).toLong(),
            difficulty = difficulties.getOrElse(index) { "Medium" },
            description = description,
            createdAt = System.currentTimeMillis()
        )
    }
}
