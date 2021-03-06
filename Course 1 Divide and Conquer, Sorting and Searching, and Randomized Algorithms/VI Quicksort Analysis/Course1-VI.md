这一章节的课程是证明随机选择pivot的快速排序算法的其平均运行时间是$O(nlogn)$，证明过程如同算法本身是巧妙的，基于一个在随机算法分析中很有用的“分解原理”。

首先定义：

1. 输入数组A的长度为n
2. 样本空间$\Omega=$快排中所有可能的随机选择，即**一组pivot序列**
3. 随机变量$C(\delta)=$对于一组pivot序列$\delta$，元素比较的总次数

有一个重要的结论是，快排的运行时间取决于元素比较的次数。可以证明的是，存在一个常数c，对于所有可能的pivot序列，有$RT(\delta)\leq c\cdot C(\delta)$。因此接下来的目标就是证明$E(C)=O(nlogn)$。如果想先通过递归关系理论来探讨$C(\delta)$是比较困难负责的，因此可以利用**分解原理**，将复杂的$C_(\delta)$分解成若干个简单的随机变量之和。

接下来定义基础构建：这里不能利用Master method，因为在快排中子问题的大小是随机变化的。定义$z_i$是输入数组A中第i小的元素。对于$\delta\in\Omega$，当$i<j$时，定义$X_{ij}=$第i小的元素与第j小的元素的比较次数。根据快排的ChoosePivot子程序中的Partition操作可知，元素的比较操作只会存在于pivot和其他元素之间，的=只有当$z_i和z_j$中有一个是pivot时，比较次数为1，其他情况都为0。因此有$C(\delta)=\sum_{i=1}^{n-1}\sum_{j=i+1}^{n}X_{ij}(\delta)$。接着利用线性期望，可得$E(C)=\sum_{i=1}^{n-1}\sum_{j=i+1}^{n}E(X_{ij})$，其中$E(X_{ij})=0\cdot P(X_{ij}=0)+1\cdot P(X_{ij}=1)$，则$E(C)=\sum_{i=1}^{n-1}\sum_{j=i+1}^{n}P(X_{ij}=1)$。

下面是求$P(X_{ij}=1)$，即给定pivot序列，则输入数组中第i小与第j小的元素被比较的概率。对于$z_i,z_j$且$i<j$，它们之间还存在其他元素，即有$z_i,z_{i+1},...,z_{j-1},z_{j}$，有一个结论是，如果选择的pivot是$z_i$或者$z_j$，则它们会被比较；反之，如果选择的pivot是$z_i$和$z_j$之间的元素，则它们不会被比较，因为它们会被pivot左右两边各自的递归继续调用。因此，而每个元素被选中为pivot的概率都是$\frac{1}{j-i+1}$，因此$P(X_{ij}=1)=\frac{2}{j-i+1}$。因此$E(C)=\sum_{i=1}^{n-1}\sum_{j=1}^{n}\frac{2}{j-i+1}$。

最后问题就归结为证明$\sum_{i=1}^{n-1}\sum_{j=1}^{n}\frac{2}{j-i+1}=O(nlogn)$。先分析$\sum_{j=1}^{n}\frac{2}{j-i+1}=\frac{1}{2}+\frac{1}{3}+...=\sum_{k=2}^n\frac{1}{k}$，根据下图可知，$\sum_{k=2}^n\frac{1}{k}\leq\int_{1}^n\frac{1}{x}dx=lnx|_{1}^n=ln$。因此，$E(C)\leq 2\cdot n\cdot ln n$。

![](http://7xwggp.com1.z0.glb.clouddn.com/logn.png)

ps: 撇开快排的证明，下面总结一下分解原理的通常步骤。

1. 确定好真正关心的随机变量Y
2. 将复杂的随机变量Y分解为若干个简单的随机变量indicator(仅仅取值0或者1)之和，即$Y=\sum_{l=1}^mX_e$
3. 最后利用线性期望来求得$E(Y)=\sum_{l=1}^mP(X_e=1)$