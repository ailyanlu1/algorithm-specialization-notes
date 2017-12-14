这一章节的课程围绕Divide and conquer分治算法的思想，讨论三个比较有意思的例子，分别是：

1. 找出给定数组中的逆序对，这个问题和计算两个排名表的相似度有关，因此又与推荐算法中的协同过滤有关
2. 矩阵乘法，利用Strassen提出的递归算法
3. 在给定平面中找出最近点对，三个问题中最具挑战性的一个

## O(nlogn) algorithm for counting inversions I/ 统计逆序对的O(nlogn)算法 I

> update：2017/12/12

这节课的视频复习了分治思想的paradigm，即先将大问题分解为小问题，再递归求解小问题，最后合并每个小问题的求解结果。此外，继续给出一个具体的问题：找出一个数组中所有的逆序对。逆序对的定义是：数据对(i,j)，使得$i<j$且$A[i]>A[j]$。给定输入数组[1,3,5,2,4,6]，可以看出其中逆序对为(3,2)，(5,2)和(5,4)。可以通过如右图所示的连线方法来求解逆序对的数量，第一行是有序，第二行是原序，将数字相同的连接起来，则交叉点的数量就是逆序对的个数。有个题外话，为什么要计算逆序对？其实它的应用场景可以是两个排名list的相似度计算，比如协同过滤。

![](http://7xwggp.com1.z0.glb.clouddn.com/inversions.png)

逆序对求解，这里只讲两种方法，暴力求解与分治求解。

1. 暴力求解：直接嵌套循环，逐个判断，时间复杂度是$O(n^2)$

2. 递归求解：先定义inversion，分为三种，如下图所示。求解过程就是先递归对左半数组求解left inversion的数量x，再递归对右半数组求解right inversion的数量y，然后求解split inversion的数量z，最后返回$x+y+z$之和就是最终结果了。这节课视频暂时没有讲解计算split inversion的实现，但是目标是以线性时间的复杂度$O(n)$来实现。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/inversions_type.png)

## O(nlogn) algorithm for counting inversions II/ 统计逆序对的O(nlogn)算法 II

> update：2017/12/13

这一节课的视频主要解决上一个视频中遗留下来的问题，即如何统计split inversions。关键点在于利用Merge sort的思路，在递归调用时，除了统计左右半数组的left inversion和right inversion外，还需要顺便对左右半数据进行排序。这就是在递归时多做一点工作来简化split inversion的统计。为什么这么做呢？因为可以发现Merge sort的merge步骤可以自然地用来统计split inversion数量。

![](http://7xwggp.com1.z0.glb.clouddn.com/sort_and_count.png)

如果左半数组B中的所有元素都小于右半数组C中的元素，那么split inversion的个数就是0。按照下图所示的merge操作，可以总结出一个结论，如果右半数组中的元素y比左半数组中的某个元素x小，则x之后剩余的所有元素都能和y组成逆序对。这是显然的，因为左右半数组前提已经是有序的了。这个统计split inversion的运行时间也是线性的，因为前面的课程已经分析了merge操作是$O(n)$，统计求和本身也是$O(n)$，则$O(n)+O(n)=O(n)$。递归调用和Merge sort一样，则找出数组中的逆序对，这个问题的总运行时间是$O(n\cdot log n)$。

![](http://7xwggp.com1.z0.glb.clouddn.com/sort_and_count_example.png)

## Strassen's subcubic matrix multiplication algorithm

这一节课的视频讲解了利用分治思想来求解矩阵乘法，先定义了问题是什么，然后讲了最naivee的求解方法，接着讲了基础分治算法，最后引出1969年的Strassen矩阵乘积算法。

1. Naivee：先假设涉及的矩阵维度都是n，$X\cdot Y=Z$，$Z_{i,j}=\sum_{k=1}^{n}X_{i,k}\times Y_{k,j}$，即结果矩阵Z的第i行第j列的元素是矩阵X的第i行与矩阵Y的第j列的点积，如下图所示。求点积的运行时间是$\Theta(n)$，因此利用矩阵乘法的定义求解乘积的运行时间是$\Theta(n^3)$。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/matrix_multiplication.png)

2. 分治求解：思想是将X划分成四个子矩阵ABCD，将Y划分成四个子矩阵EFGH，因此A-H都是$\frac{1}{2}n\times \frac{1}{2}n$的矩阵。这样，$X\cdot Y=\left\{\begin{matrix}AE+BG& AF+BH\\ CE+DG & CF+DH\end{matrix} \right\}$。因此，求解过程可以分为两部：

   - Step-1，递归计算出上面8个子矩阵乘积
   - Step-2，求和，这一步的运行时间为$\Theta(n^2)$

   实际上，这个算法的运行时间仍然是$\Theta(n^3)$，但是这不是坏事，回顾之前利用分治思想计算整数乘积，其中有一个步骤是减少一次递归调用。按照这个想法，引出下面要讲的Strassen算法。

3. Strassen求解：这是一个经典的算法，1969年提出的，它只递归求解7个子矩阵乘积，虽然只少了一个，但是运行时间却是sub-cubic的。求解过程和分治求解一致，稍微不同的就是step-1只求解7个，step-2除了addition还有一部分substraction操作。那么，7个子矩阵乘积是什么？利用$P_1-P_7$表示如下。

   - $P_1=A(F-H)$
   - $P_2=(A+B)H$
   - $P_3=(C+D)E$
   - $P_4=D(C+E)$
   - $P_5=(A+D)(E+H)$
   - $P_6=(B-D)(G+H)$
   - $P_7=(A-C)(E+F)$

   则利用下面的方法可以得出$X\cdot Y=\left\{\begin{matrix}AE+BG& AF+BH\\ CE+DG & CF+DH\end{matrix} \right\}=\left\{\begin{matrix}P_5+P_4-P_2+P_6 & P_1+P_2\\ P_3+P_4 & P_1+P_5-P_3-P_2\end{matrix}\right\}$

   关于后面的公式如何得到的，Strassen是如何想到这个方法的，以及它的时间复杂度分析，后续课程将给出！



