这一章节的课程主要介绍Master method，一种通用的分析分治算法运行时间的数学工具，先给出motivation，再介绍形式化描述，接着讲解6个示例，最后讨论Master method的证明。

## Motivation / 动机

> update：2017/12/18

潜在有用的算法通常需要数学分析来评估其性能，回顾小学时期的整数相乘算法使用$\Theta(n^2)$的运行时间来计算两个n位整数的乘积$x\times y$。递归算法是将x分解为$x=10^{\frac{1}{2}n}a+b$，将y分解为$y=10^{\frac{1}{2}n}c+d$，则$x\times y = 10^nac+10^{\frac{1}{2}n}(ad+bc)+bd$。即递归计算$ac,ad,bc,bd$的乘积即可。令T(n)表示计算两个n位整数乘积过程中操作的数量，基线条件是$T(1)\leq $常数，对于$n\geq 1$，有$T(n)\leq 4T(\frac{1}{2}n)+O(n)$。此外，一个更好的递归算法是减少一次递归调用，只计算ac,bd与(a+b)(c+d)，而ad+bc=(a+b)(c+d)-ac-bd。这样，对于$n\geq1$，有$T(n)\leq3T(\frac{1}{2}n)+O(n)$。

## Formal statement / 形式化叙述

> update：2017/12/18

这一节课程视频主要介绍Master method的形式化叙述，可以理解为解决递归问题的一个black box，有一个前提假设，即所有的子问题拥有相同的size。其形式化表述如下：

1. 首先是基线条件Base Case：当n足够小时，T(n)为常数

2. 通常递归调用的情况下，对于较大的n，假设有$T(n)\leq a\cdot T(\frac{n}{b})+O(n^d)$，其中a、b、d都是常数，与n大小无关

   - a是递归调用的次数($\geq 1$)
   - b是输入大小收缩系数($> 1$)
   - d是“合并阶段”运行时间的指数($\geq 0$)

   则有$ T(n)=\begin{cases}O(n^dlogn) &a = b^d \\ O(n^d) & a < b^d \\O(n^{log_ba}) & a > b^d  \end{cases}$

   需要注意的是，当$a=b^d$时，big-O里面的log基数省略没写，这是因为基数具体为哪个值并不重要，不同基数带来的变化是常量级别的；而当$a>b^d$时，由于log是在指数的位置，所以基数是哪个值就很重要了，不能忽略。

## Examples / 示例

> update：2017/12/18

这一节课程主要是利用6个具体示例来说明Master method。

### 示例1：Merge sort

首先确定a、b、d的数值：

- a=2
- b=2
- d=1

满足$a=b^d$，因此Merge sort的$T(n)=O(n^dlogn)=O(nlogn)$

### 示例2：有序数组的二分查找

首先确定a、b、d的数值：

- a=1
- b=2
- d=0

和Merge sort不同，只需要将要查找的数与中间元素比较，如果小于中间元素，则递归调用左半部分，否则调用右半部分，所以a的值为1；递归之外的操作就是元素的比较，因此d的值为0。由于满足$a=b^d$，因此有序数组的二分查找的$T(n)=O(n^dlogn)=O(logn)$

### 示例3：整数相乘的递归算法I

首先确定a、b、d的数值：

- a=4
- b=2
- d=1

整数相乘的基础递归算法总共有4次调用，所以a=4；每一次调用的整数位数是原来的一半，所以b=2；递归调用之外的加操作是线性的，所以d=1。由于满足$a>b^d$，因此整数相乘的基础递归算法的$T(n)=O(n^log_ba)=O(n^2)$

### 示例4：整数相乘的递归算法II

首先确定a、b、d的数值：

- a=3
- b=2
- d=1

整数相乘的改进递归算法总共有3次调用，所以a=3；每一次调用的整数位数是原来的一半，所以b=2；递归调用之外的加操作是线性的，所以d=1。由于满足$a>b^d$，因此整数相乘的基础递归算法的$T(n)=O(n^log_ba)=O(n^{log_23})=O(n^{1.59})$

### 示例5：Strassen矩阵乘法

首先确定a、b、d的数值：

- a=7
- b=2
- d=2

Strassen矩阵乘法是递归调用7次$P_1$~$P_7$加减操作，所以a=7；$P_1$~$P_7$子矩阵的规模是原始矩阵的一半，所以b=2；递归调用之外的加操作规模是子矩阵的大小，因此d=1。由于满足$a>b^d$，因此整数相乘的基础递归算法的$T(n)=O(n^log_ba)=O(n^{log_27})=O(n^{2.81})$

### 示例6：Fictitious recurrence for $a<b^d$

假设a、b、d的数值：

- a=2
- b=2
- d=2

由于满足$a<b^d$，因此$T(n)=O(n^d)=O(n^2)$

## Proof I / 证明 I

> update：2017/12/18

这一节课程主要是介绍Master method的证明。这个证明是概念性层面上的，理解Master method的三种case的概念性意义以及对于的recursion tree是有很大帮助的。如果理解了，就不用对Master method的三种case进行死记硬背。

证明的前提假设如下：

1. $T(1)\leq c$
2. $T(n)\leq a\cdot T(\frac{n}{b})+c\cdot n^d$
3. n是b的幂次方

按照Merge sort讲解recursion tree的思路，在第j层，有$a^j$次方个子问题，且每个子问题的输入规模是$\frac{n}{b^j}$。层数j的取值范围是[0,1,...,$log_bn$]。分析第j层的总工作量为$\leq a^j\cdot c\cdot [\frac{n}{b^j}]^d=c\cdot n^d\cdot (\frac{a}{b^d})^j$，其中$(\frac{n}{b^j})^d$是第j层每个子问题的总工作量，对公式进行化简整理，使得与j相关、无关的变量分离。对所以层的工作量求和，即$\leq c\cdot n^d\cdot \sum_{j=0}^{log_bn}(\frac{a}{b^d})^j$。后续视频会讲解这个式子与Master method的三种case之间的关联。

## Interpretation of the 3 cases / 3种情况的演绎

> update：2017/12/18

上一节课程已经介绍了第j层的工作量上界是$c\cdot n^d\cdot(\frac{a}{b^d})^j$。令a为子问题增生的速率RSP，$b^d$是每个子问题工作量的收缩速率RWS，有下面三种结论：

1. 如果RSP<RWS，则每一层工作量随着层数j而递减，recursion tree根节点处的工作量更多，起决定性作用，而根节点的输入规模是n，因此$T(n)=O(n^d)$
2. 如果RSP>RWS，则每一层工作量随着层数j而递增，recursion tree叶子节点处的工作量更多，起决定性作用
3. 如果RSP=RWS，则每一层的工作量是相同的，而层数规模是$O(logn)$，每一层的工作量为$O(n^d)$，因此$T(n)=O(n^dlogn)$

## Proof II / 证明 II

> update：2017/12/18

前面的两节课程分别分析了recursion tree的工作量(放大聚焦于给定的第j层工作量，然后对所有层的工作量求和)，以及赋予它三种直观语义。这一节课程继续完成Master method的精确证明。

首先回顾一下总工作量：$\leq c\cdot n^d\cdot \sum_{j=0}^{log_bn}(\frac{a}{b^d})^j \star$

1. 当$a=b^d$时，$\star=c\cdot n^d (log_bn+1)=O(n^dlogn)$

2. 在讨论另外两种语义时，先回顾一下等比数列求和，当$r\neq1$时，我们有$1+r+r^2+...+r^k=\frac{r^{k+1}-1}{r-1}$。当$r<1$时，$r^{k+1}$趋近于0，因此和趋近于$\frac{1}{1-r}$是一个常数；当$r>1$时，和$\leq r^k(1+\frac{1}{r-1})$

   - 当$a<b^d$，即$r<1$，则$\star=O(n^d)$
   - 当$a>b^d$，即$r>1$，则$\star=O(n^d\cdot(\frac{a}{b^d})^{log_bn})$，由于$b^{-dlog_bn}=(b^{log_bn})^-d=n^{-d}$，所以$\star=O(n^d\cdot a^{log_bn}\cdot n^{-d})$，而$a^{log_bn}$是recursion tree叶子节点的规模，且$a^{log_bn}=n^{log_ba}$(左右两边同时取对数logb)，所以$\star=O(n^log_ba)$

   ​