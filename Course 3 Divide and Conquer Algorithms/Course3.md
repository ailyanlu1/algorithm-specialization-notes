这一章节的课程围绕Divide and conquer分治算法的思想，讨论三个比较有意思的例子，分别是：

1. 找出给定数组中的逆序对，这个问题和计算两个排名表的相似度有关，因此又与推荐算法中的协同过滤有关
2. 矩阵乘法，利用Strassen提出的递归算法
3. 在给定平面中找出最近点对，三个问题中最具挑战性的一个

## Counting inversions I/ 统计逆序对I

> update：2017/12/12

这节课的视频复习了分治思想的paradigm，即先将大问题分解为小问题，再递归求解小问题，最后合并每个小问题的求解结果。此外，继续给出一个具体的问题：找出一个数组中所有的逆序对。逆序对的定义是：数据对(i,j)，使得$i<j$且$A[i]>A[j]$。给定输入数组[1,3,5,2,4,6]，可以看出其中逆序对为(3,2)，(5,2)和(5,4)。可以通过如右图所示的连线方法来求解逆序对的数量，第一行是有序，第二行是原序，将数字相同的连接起来，则交叉点的数量就是逆序对的个数。有个题外话，为什么要计算逆序对？其实它的应用场景可以是两个排名list的相似度计算，比如协同过滤。

![](http://7xwggp.com1.z0.glb.clouddn.com/inversions.png)

逆序对求解，这里只讲两种方法，暴力求解与分治求解。

1. 暴力求解：直接嵌套循环，逐个判断，时间复杂度是$O(n^2)$

2. 递归求解：先定义inversion，分为三种，如下图所示。求解过程就是先递归对左半数组求解left inversion的数量x，再递归对右半数组求解right inversion的数量y，然后求解split inversion的数量z，最后返回$x+y+z$之和就是最终结果了。这节课视频暂时没有讲解计算split inversion的实现，但是目标是以线性时间的复杂度$O(n)$来实现。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/inversions_type.png)

## Counting inversions II/ 统计逆序 II

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


##  Closest pair I / 最近点对问题 I

> update：2017/12/17

这节课的视频讲解的是最近点对问题，要求找出给定平面上距离最近的两个点。这个问题是分治求解问题的经典算法之一，在其他应用领域也会经常遇到这个问题，比如计算机图形学、机器人等。

首先是问题的形式化定义：给定一个平面内的若干个点，每个点由其横坐标x与纵坐标y的序列定义。两个点的距离是指欧氏距离(坐标差的平方和开根号)。最近点对问题就是要求找出一对点，使得它们的距离是所有点对中最小的。

求解的前提假设：不存在ties，即所有端点的x坐标不同，y坐标也不同。没有这个前提假问题也能解决，这里只是为了方便算法的论述。

沿袭前面的课程统计数组中的inversion求解过程：如果允许平方的运行时间，依旧可以利用暴力求解找出最近点对，即嵌套循环遍历所有不同的点对，计算各自的距离，最终找出最短的。但是，是否存在与counting inversion一样O(nlogn)的求解？在1-dimension的情况下，所有点在一条坐标轴上，可以先排序O(nlogn)，再遍历一遍找出最短的O(n)。

2-dimension情况下O(nlogn)的high-level求解：

1. Preprocessing：对输入点集按照x坐标排序得到点集$P_x$，再根据y坐标排序得到点集$P_y$
2. 利用分治求解

2-dimension情况下O(nlogn)的具体求解ClosestPair($P_x，P_y$)：

1. 根据x坐标把原始点集划分成左右两部分，Q与R，再利用Preprocessing分别得到x与y轴有序的点集$Q_x，Q_y，R_x，R_y$
2. 递归调用ClosestPair($Q_x，Q_y$)=$(p_1,q_1)$得到左半点集中的最近点对
3. 递归调用ClosestPair($R_x，R_y$)=$(p_2，q_2)$得到右半点集中的最近点对
4. $\delta=min(d(p_1,q_1)，d(p_2,q_2))$
5. 调用CloestSplitPair($P_x，P_y，\delta$)得到最近点对($p_3，q_3$)，因为两个点可能各自位于Q与R两个点集中，这一步的运行时间是O(n)线性的
6. 返回$min(d(p_1,q_1)，d(p_2,q_2)，d(p_3,q_3))$

求解Split closest pair的subroutine要求：O(n)线性运行时间，始终是正确的无论最近点对是否是split closest pair，具体求解过程如下CloestSplitPair($P_x，P_y，\delta​$)：

1. 过滤，修剪掉部分不需要的点，只考虑部分点集，而这部分点集位于一条位于整体点集中部的垂直的宽带中(令$\bar{x}=$原始点集左半部分的最大x坐标，这个操作是O(1)的，因为在Preprocessing中已经对左半点集按照x坐标进行了排序)

2. 利用$\delta$来决定垂直宽带的宽度：$2\times \delta$，即以$\bar{x}$为中心先，左右两边各取$\delta$得宽度，如下图所示。这样就忽略到不在这个宽带中得点，接下来的操作只针对位于宽带中的部分点，这部分点的x坐标的上下界为[$\bar{x}-\delta，\bar{x}+\delta$]

   ![](http://7xwggp.com1.z0.glb.clouddn.com/strip.png)

3. 对宽带中的点集按照y坐标排序得到点集$S_y$，可以直接从$P_y$中按照上一步的要求进行提取，因此这一步的操作是O(n)的

4. 遍历$S_y$中的点，找出距离小于$\delta$的最短点对，具体过程如下图所示。首先是初始化$best$与$best_pair$，分为用来记录最短点对的距离与最短点对本身；接着是嵌套循环遍历$S_y$，由于内层嵌套的迭代次数是常量7，因此内层的运行时间是O(1)，外层的运行时间是O(n)，总体的运行时间依旧是O(n)。**如果最终找出距离小于$\delta$的点对(p,q)，则p与q在$S_y$中至多相间7个点。**以上的证明在下个视频课程给出。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/iterate_sy.png)

   ## Closest pair II / 最近点对问题 II

   > update：2017/12/18

   这节视频主要是证明上一节求解ClosestSplitPair中的结论：令$p\in Q, q\in R$是一对split pair，且$d(p,q)<\delta$，则有

   1. p和q是$S_y$中的两个点
   2. p与q在$S_y$中的位置最多间隔7个点

   令p的坐标是$(x_1,y_1)$，q的坐标是$(x_2，y_2)$，p来自左半点集Q，q来自右半点集R，且$d(p,q)<\delta$，则有$|x_1-x_2|\leq \delta$，且$|y_1-y_2|\leq \delta$。

   证明第一个结论，就是要证明$x_1,x_2\in [\bar{x}-\delta，\bar{x}+\delta]$。由于p来自左半点集Q，则必有$x_1\leq\bar{x}$，同样q来自右半点集R，则有$x_2\geq\delta$。由于这里与y轴坐标无关，可以借助1-dimension的坐标轴来证明这个。如下图所示，由于$|x_1-x_2|\leq\delta$，所以如果$x_1<\bar{x}-\delta$，则$x_2$肯定必须小于$\bar{x}$，这就矛盾了。同样，如果$x_2>\bar{x}-\delta$，则$x_1$肯定必须大于$\bar{x}$。因此，$x_1$和$x_2$肯定介于$\bar{x}-\delta$与$\bar{x}+\delta$之间。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/claim1.png)

   证明第二个结论，关键是画出如下图所示的8个box，这些box的横坐标必定包含p和q两个点，介于$\bar{x}-\delta$与$\bar{x}+\delta$之间，box的bottom是p和q两个中较小的y坐标，$|y_1-y_2|\leq \delta$，因此单个盒子的高度是$\frac{1}{2}\delta$。在证明之前，先证明两个辅助定理。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/box.png)

   - 辅助定理1：纵坐标位于p与q之间、且属于$S_y$的点必定存在于这8个box之中。首先关于$S_y$的定义，x坐标必须满足介于$\bar{x}-\delta$与$\bar{x}+\delta$之间；其次，p与q的y坐标之差绝对值是小于$\delta$的，这是upper bound。
   - 辅助定理2：每个box中至多存在一个点。可以用反证法证明，假设a和b两个点存在于同一个盒子中，那个它们必定要么都来自Q，要么都来自R，且它们的距离$d(a,b)\leq \frac{\sqrt{2}}{2}\delta\leq\delta$，这就与原始条件(split pair分别来自Q与R且$\delta$本身已经是Q或者R中最短的距离)相矛盾。

   结合以上两个辅助定理，可以推出包含p和q在内，这8个box中总共至多包含8个点。因此p与q至多相隔7个点。