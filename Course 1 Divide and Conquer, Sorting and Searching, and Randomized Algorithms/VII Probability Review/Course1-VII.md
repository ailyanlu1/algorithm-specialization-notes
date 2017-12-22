这一章节的课程分了两个视频，首先回顾了离散概率、样本空间、事件、随机变量、期望值的概念，其次主要介绍条件概率与独立事件。学习这些是为后续随机压缩算法的分析做准备。

## Sample spaces / 样本空间

样本空间$\Omega$表示所有可能发生的结果，在算法设计中，$\Omega$通常是有限的。对于样本空间中的每一个结果i，其发生概率$p(i)\geq0$，且所有结果发生概率之和$\sum_{i\in\Omega}p(i)=1$。

样本空间举例：

1. 掷两个六面骰子，则$\Omega=\{(1,1),(1,2),...,(6,5),(6,6)\}$，共有36个结果
2. 一次QuickSort调用中的pivot选择，$\Omega=\{1,2,...,n\},p(i)=\frac{1}{n}$

## Events / 事件

事件$E$表示一个样本空间的子集，即$E\in\Omega$，事件E发生的概率是其拥有所有结果的发生概率之和，即$P(E)=\sum_{i\in E}p(i)$。

事件举例：

1. 掷两个六面骰子，则点数之和为7的事件概率是多少？

   枚举可知$E=\{(1,6),(6,1),(2,5),(5,2),(3,4),(4,3)\}$，则$P(E)=\frac{6}{36}=\frac{1}{6}$

2. 一次QuickSort调用ChoosePivot，则将输入数组划分成比25-75更好的划分事件概率是多少？

   可知E={第$(\frac{n}{4}+1)^{th}$小元素，...，第$(\frac{3n}{4})^{th}$小元素}，则$P(E)=\frac{1}{2}$

## Random Variables / 随机变量

随机变量$X$是一个实数函数，即$X:\Omega\rightarrow R$

## Expectation / 期望值

期望就是随机变量$X$的平均值，使用$E(X)$表示，有$E(X)=\sum_{i\in\Omega}X(i)\cdot p(i)$

## Linearity of Expectation / 线性期望

令$X_1,X_2,...,X_n$是定义在样本空间$\Omega$上的一组随机变量，则有$E[\sum_{j=1}^{n}X_j]=\sum_{j=1}^{n}E[X_j]$。这里对随机变量没有要求，即它们未必是独立的，这个结论在任何情况下都成立。

线性期望举例：掷骰子，令$X_1$表示第一个骰子的点数，$X_2$表示第二个骰子的点数，则两个骰子的点数之和的期望$E(X_1+X_2)=E(X_1)+E(X_2)$,而$E(X_1)=E(X_2)=\frac{1}{6}\cdot(1+2+3+4+5+6)=3.5$，则$E(X_1+X_2)=7$

线性期望证明：$\sum_{j=1}^{n}E[X_j]=\sum_{j=1}^n\sum_{i\in\Omega}X_j(i)p(i)=\sum_{i\in\Omega}\sum_{j=1}^nX_j(i)p(i)=\sum_{i\in\Omega}p(i)\sum_{j=1}^{n}X_j(i)=E[\sum_{j=1}^nX_j]$，如下图所示，每个单元的求和就是结果，区别就在于$\sum_{j=1}^n\sum_{i\in\Omega}X_j(i)p(i)$是先求每列的值，再把每列的值相加；而$\sum_{i\in\Omega}\sum_{j=1}^nX_j(i)p(i)$是先求每行的值，再把每行的值相加。

![](http://7xwggp.com1.z0.glb.clouddn.com/linearity_expectation.png)

## Load balancing / 负载均衡示例

通过一个例子把前面所有的知识点串联起来。

场景：将n个进程分配到n个服务器上。

解决方案：将每个进程随机指派到一个服务器上。

问题：一个服务器被分配到进程数量的期望？

样本空间$\Omega$是所有$n^2$种分配可能情况，每一种发生的概率相同。

令$Y$为指派到第一台服务器上的进程的总数量，目标是求$E(Y)$。

令$X_j=\begin{cases}1 & 如果第j个进程指派给该服务器\\ 0&其他情况\end{cases}$

则有$Y=\sum_{j=1}^nX_j$，利用线性期望可得$E(Y)=E(\sum_{j=1}^nX_j)=\sum_{j=1}^nE(X_j)=\sum_{j=1}^nP(X_j=0)\cdot 0+P(X_j=1)\cdot 1=\sum_{j=1}^n\frac{1}{n}=1$

## 条件概率

令$X,Y\in\Omega$为两个事件，它们的关系如下图所示，则在已知事件Y发生概率P(Y)的情况下，事件X的发生概率$P(X|Y)=\frac{P(X\cap Y)}{P(Y)}$。

![](http://7xwggp.com1.z0.glb.clouddn.com/conditional_probability.png)

条件概率示例：掷两个六面骰子，在给定两个骰子点数之和为7的前提下，至少有一个骰子的点数为1的概率是多大？设事件E为“至少有一个骰子的点数为1”，事件Y为“两个骰子的点数之和为7”，则有$Y=\{(1,6),(6,1),(2,5),(5,2),(3,4),(4,3)\}$,$X\cap Y=\{(1,6),(6,1)\}$，因此$P(X|Y)=\frac{P(X\cap Y)}{P(Y)}=\frac{2/36}{6/36}=\frac{1}{3}$.

## 独立与独立的随机变量

令$X,Y\in\Omega$为两个事件，则当且仅当$P(X\cap Y)=P(X)\cdot P(Y)$时事件X与Y是相互独立的。相互独立这个概念是比较微妙的，有的时候你觉得两个事件是相互独立的，但是直觉未必是正确的！

随机变量的独立定义是：假设A和B是定义在样本空间$\Omega$上的两个随机变量，当且仅当对于所有$A=a$和$B=b$都有$P(A=a \cap B=b)=P(A=a)\cdot P(B=b)$时，A与B是相互独立的。关于相互独立的两个随机变量有一个结论，即$E(AB)=E(A)\cdot E(B)$，证明如下：

$E(AB)=\sum_{a,b}(a\cdot b)\cdot P(A=a \cap B=b)=\sum_{a,b}(a\cdot b)\cdot P(A=a)\cdot P(B=b)=(\sum_{a}a\cdot P(A=a))\cdot (\sum_{b}b\cdot P(B=b)=E(A)\cdot E(B)$.

独立的随机变量示例：令$X_1,X_2\in \{0,1\},X_3=X_1 xor X_2$,则样本空间$\Omega=\{000,101,011,110\}$，每一个发生结果等概率。则事件$X_1$与$X_3$显然是相互独立的随机变量，而$X_1\cdot X_3$与$X_2$不是，因为$E(X_1X_2X_3)=0$,而$E(X_1X_3)\cdot E(X_2)=E(X_1)\cdot E(X_3)\cdot E(X_2)=\frac{1}{8}$。



