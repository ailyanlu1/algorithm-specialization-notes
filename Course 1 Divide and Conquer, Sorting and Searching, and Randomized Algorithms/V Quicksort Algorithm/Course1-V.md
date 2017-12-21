这一章节的课程主要是关于快速排序，它是一个经典的随机算法，会介绍伪码、如何以线性的时间以及最小的额外空间来围绕一个pivot对一个数组进行划分、不同pivot的选择对性能的影响以及阐述算法是如何运行的。

## Quick Sort overview / 快速排序简介

> update：2017/12/19

QuickSort快速排序是一个很经典、著名的算法，很实用，是由图灵奖获得者Hoare提出的，在现实应用中经常比Merge Sort更加高效。它是一个很巧妙的算法，它的代码言简意赅。可以通过数学分析来解释为什么Quick Sort可以运行得很快，它的平均运行时间是$O(nlogn)$。和Merge Sort不同的是，QuickSort直接在原始数组的位置上重复地进行swap操作，只需要很少的额外空间。

首先回顾一下排序问题，假设不包含重复数字：

- 输入是包含n个无序数字的数组
- 输出是包含n个递增有序数字的数组

QuickSort的关键子程序是“围绕一个pivot对数组进行划分”，即从数组中选择一个数字作为pivot(后面会介绍选择哪一个数字作为pivot)，然后把所有小于pivot的数字放置在pivot左边，所有大于pivot的数字放置在pivot右边，这样就把数组划分为两个bucket。如下图所示，选择第一个数字3作为pivot，重新排序数组，使得所有小于3的数字位于左边，所有大于3的数字位于右边。在这一阶段，不关注bucket内部是否是有序的。所以划分操作的直接目的是**把pivot放置在正确的位置上**，最终目的是排序。

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


## Partitioning around a pivot / 以pivot为分界点划分

> update：2017/12/20

这一节课程主要讲Paritioning around a pivot的具体实现，这里不考虑pivot是如何挑选的，为了方便，选择数组的第一个元素为pivot。pivot左边或者右边的元素暂时不需要有序，只要求左边的元素均比pivot小，右边的元素均比pivot大。

关于这个Partition操作，上一节课程介绍了两个结论，其中一个是说这个操作的运行时间是线性的O(n)，且操作是in-place的，不需要额外的空间。假设现在允许使用额外的线性空间，就能够事先分配一个长度为n的数组B，接着直接扫描输入数组A，把比pivot小的元素从B头部往后插入，把比pivot大的元素从B尾部往前插入，如下图所示。

![](http://7xwggp.com1.z0.glb.clouddn.com/non-in-place.png)

In-place的Partition实现：假设pivot是输入数组的第一个元素(如果不是，可以通过将pivot和第一个元素交换一下，就能继续使用下面讲的方法)。由于这个Partition操作是对输入数组进行一次扫描，所以在这个过程中，需要追踪已经扫描到的数据和未扫描到的数据的分界，如下图所示。已经扫描到的数据表示已经进行了Parititon，且该部分数据以pivot为分界点进行了划分。

![](http://7xwggp.com1.z0.glb.clouddn.com/high-level-idea.png)

下面通过一个具体的示例来更直观地介绍Paritition处理流程。首先维护两个index，j表示已经扫描到和未扫描到的分界，i表示已经扫描到中小于pivot与大于pivot的分界。初始时i和j都指向pivot后一个元素。

- 当前元素为8，大于pivot，单独构成partitioned部分，j后移，i不变

- 当前元素为2，小于pivot，交换i与j当前指向的元素，即交换8和2，j与i同时后移，2和8构成partitioned，剩余的为unpartitioned

- 当前元素为5，大于pivot，2、8和5构成partitioned，剩余的为unpartitioned，j后移，i不变

- 当前元素为1，小于pivot，交换当前i与j指向的元素，即交换8和1，j和i同时后移，2、1、5和8构成partitioned，剩余的为unpartitioned

- ...(4、7和6均同8、5的处理相同)

- 最后，交换pivot和i-1指向的元素

  ![](http://7xwggp.com1.z0.glb.clouddn.com/partition_example.png)

最后给出Partition操作的伪码，输入数组为A，l和r分别是A的首尾边界：

```python
Partiton(A, l, r)
  p = A[l]
  i = l + 1
  for j = l + 1 to r 
  	if A[j] < p
    	swap A[j] and A[i]
      	i++
  swap A[i-1] and A[l]
```

分析一下其运行时间，由于$n=r-l+1$是输入数组的长度，且每个元素的工作量是O(1)，并且处理是in-place的，所以总的运行时间是O(n)线性的。

## Correctness of QuickSort / 快速排序的正确性

> update：2017/12/20

这一节课程的目的是证明QuickSort算法是正确的。虽然遇到一个问题，能够设计出一个分治算法来解决它比较困难，但是我们也总希望自己能理解为什么它这么做是正确的，有的时候需要证明自己的直觉。这里以QuickSort为例来证明，首先pivot的选择只会影响算法的性能，不会影响想法的正确性。利用归纳假设法来证明。归纳假设里的P(n)在QuickSort中是指"对于任意长度n的输入数组，QuickSort总能正确地对其进行排序"。归纳假设证明分两步：

1. 当n=1时，输入数组只有一个元素，显然已经是有序的，因此QuickSort是正确的

2. 当$n\geq 2$时，假设$P(k)$对于所有$k<n$是成立的，则要证明$P(n)$也是正确的。回顾QuickSort首次围绕pivot p将输入数组A划分成两部分，如下图所示。划分操作完之后，pivot就位于最终有序的位置上了，令$k_1$和$k_2$分为是两个划分子数组的长度，有了"P(k)对于所有k<n都成立"的假设，则递归调用后，pivot左边的子数组是有序的，pivot右边的子数组也是有序的，加上pivot比左半子数组所有元素都大，且比右半子数组所有元素都小，因此最终的数组时有序的，即证明了P(n)成立。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/partition.png)


##  Choose a good pivot / pivot的选择

> update：2017/12/20

QuickSort的第一步是选择输入数组的pivot，并以此为分界点进行划分。QuickSort的运行时间取决于所选pivot的质量。质量较高的pivot意味着以此为分界点可以将输入数组划分成较为均等的两个子数组；而质量较低的pivot意味着以此为分界点会将输入数组划分成两个极不均衡的子数组。举个例子，如果输入数组是有序的，QuickSort总是选择第一个元素为pivot，则运行时间是$\Theta(n^2)$。如下图所示，由于输入数组本身是有序的，所以以第一个元素为分界点进行划分，左半数组大小为0，右半数组大小为n-1；以此类推，每次Partition的工作量是至少输入数组的长度，则总工作量$\geq n+(n-1)+(n-2)+...+1=\Theta(n^2)$，因此这种情况下QuickSort性能是最差的。

![](http://7xwggp.com1.z0.glb.clouddn.com/worst_quicksort.png)

最好的情况是每次选择的pivot是输入数组的中位数，这样每次将数组划分成均等的子数组，这种情况下，运行时间$T(n)\leq 2\cdot T(\frac{1}{2}n)+\Theta(n)$，利用Master Method可知$T(n)=\Theta(nlogn)$。

问题的关键是，不可能每次选中的pivot都是中位数。则解决方案是随机选择一个元素作为pivot。我们希望的是随机选择的pivot**足够好**。Randomization是算法设计的一个成功突破，利用随机，可以让算法更加巧妙，更加简单，更易于编码实现，性能更快，或者说，不使用随机可能很难简单地设计出来一个算法出来。后续课程会给出数学分析，证明关于QuickSort的一个定理：对于每次长度为n的输入数组，随机选取pivot，QuickSort的平均运行时间是$O(nlogn)$。这个定理适用于任何输入，没有对输入数据的假设；Average是针对算法的随机决策而言。