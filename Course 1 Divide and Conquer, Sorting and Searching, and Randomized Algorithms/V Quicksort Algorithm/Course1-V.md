这一章节的课程主要是关于快速排序，它是一个经典的随机算法，会介绍伪码、如何以线性的时间以及最小的额外空间来围绕一个pivot对一个数组进行划分、不同pivot的选择对性能的影响以及阐述算法是如何运行的。

## Quick Sort overview / 快速排序简介

> update：2017/12/19

Quick Sort快速排序是一个很经典、著名的算法，很实用，是由图灵奖获得者Hoare提出的，在现实应用中经常比Merge Sort更加高效。它是一个很巧妙的算法，它的代码言简意赅。可以通过数学分析来解释为什么Quick Sort可以运行得很快，它的平均运行时间是$O(nlogn)$。和Merge Sort不同的是，Quick Sort直接在原始数组的位置上重复地进行swap操作，只需要很少的额外空间。

首先回顾一下排序问题，假设不包含重复数字：

- 输入是包含n个无序数字的数组
- 输出是包含n个递增有序数字的数组

Quick Sort的关键子程序是“围绕一个pivot对数组进行划分”，即从数组中选择一个数字作为pivot(后面会介绍选择哪一个数字作为pivot)，然后把所有小于pivot的数字放置在pivot左边，所有大于pivot的数字放置在pivot右边，这样就把数组划分为两个bucket。如下图所示，选择第一个数字3作为pivot，重新排序数组，使得所有小于3的数字位于左边，所有大于3的数字位于右边。在这一阶段，不关注bucket内部是否是有序的。所以划分操作的直接目的是**把pivot放置在正确的位置上**，最终目的是排序。

![](http://7xwggp.com1.z0.glb.clouddn.com/pivot.png)

关于划分操作，有两个结论：

1. 这个操作的运行时间是线性的$O(n)$，且不需要额外的空间
2. 这个操作减少了problem size，能够继续使用分治算法来进行排序

最后介绍Quick Sort的整体处理流程，输入数组A以及长度n：
1. 如果数组A只包含一个元素，则直接返回

2. 调用ChoosePivot(A，n)选择pivot p

3. 以p为分界点将A进行划分，如下图所示，左半部分的元素都小于p，右半部分的元素都大于p

   ![](http://7xwggp.com1.z0.glb.clouddn.com/partition.png)

4. 递归对左半部分子数组进行QuickSort

5. 递归对右半部分子数组进行QuickSort
