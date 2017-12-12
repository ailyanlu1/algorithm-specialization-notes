## O(nlogn) algorithm for counting inversions / 统计逆序对的O(nlogn)算法

> update：2017/12/12

这节课的视频复习了分治思想的paradigm，即先将大问题分解为小问题，再递归求解小问题，最后合并每个小问题的求解结果。此外，继续给出一个具体的问题：找出一个数组中所有的逆序对。逆序对的定义是：数据对(i,j)，使得$i<j$且$A[i]>A[j]$。给定输入数组[1,3,5,2,4,6]，可以看出其中逆序对为(3,2)，(5,2)和(5,4)。可以通过如右图所示的连线方法来求解逆序对的数量，第一行是有序，第二行是原序，将数字相同的连接起来，则交叉点的数量就是逆序对的个数。有个题外话，为什么要计算逆序对？其实它的应用场景可以是两个排名list的相似度计算，比如协同过滤。

![](http://7xwggp.com1.z0.glb.clouddn.com/inversions.png)

逆序对求解，这里只讲两种方法，暴力求解与分治求解。

1. 暴力求解：直接嵌套循环，逐个判断，时间复杂度是$O(n^2)$

2. 递归求解：先定义inversion，分为三种，如下图所示。求解过程就是先递归对左半数组求解left inversion的数量x，再递归对右半数组求解right inversion的数量y，然后求解split inversion的数量z，最后返回$x+y+z$之和就是最终结果了。这节课视频暂时没有讲解计算split inversion的实现，但是目标是以线性时间的复杂度$O(n)$来实现。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/inversions_type.png)