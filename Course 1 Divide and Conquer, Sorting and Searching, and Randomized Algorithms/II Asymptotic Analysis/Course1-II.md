## The gist / 要旨

> update：2017/12/11

Asymptotic analysis是设计和分析算法的vocabulary，比如大O表示法：

- 从较高的层次分析算法的sweet spot
- 足够coarse来压缩依赖于系统/编程语言/编译器的细节
- 足够sharp来进行不同算法之间的有效对比，尤其是针对大规模输入集

High-level Idea：比如$6\cdot n\cdot log_2n$== $n\cdot log n$，也就是说运行时间是$O(n\cdot log n)$

1. 压缩常数因子：依赖于系统/编程语言/编译器等
2. 压缩低阶因子：与输入规模的增大无关

举了四个例子：

1. 判断数组A中是否包含给定的整数t，$O(n)$
2. 判断数组A或者B中是否包含给定的整数t，$2\cdot n = O(n)$
3. 判断数组A和B中是否包含相同的整数，$O(n^2)$
4. 判断数组A中是否包含重复的整数，$\frac{1}{2}n\cdot (n+1) = O(n^2)$

##  Big-Oh notation / 大O表示法

> update：2017/12/11

这一节课讲解大O表示法的定义。令T(n)是n的函数f(n)，通常表示一个算法的worst case运行时间，那么问题是：什么时候$T(n)=O(f(n))$？答案是，当n足够大，最终T(n)的上限是一个常数与f(n)的乘积。下图所示，给出形式化定义：

当存在常数c和$n_0$，对于所有的$n\geq n_0$，有$T(n)\leq c\cdot f(n)$，则称$T(n) = O(f(n))$。其中，常数c和$n_0$与n无关。

![](http://7xwggp.com1.z0.glb.clouddn.com/bigoh.png)

## Basic examples / 基础示例

> update：2017/12/11

这节课的视频给出两个有关大O表示法的示例证明。

1. ![](http://7xwggp.com1.z0.glb.clouddn.com/bigoh_example1.png)

2. 利用反证法证明

   ![](http://7xwggp.com1.z0.glb.clouddn.com/bigoh_example2.png)

## Big omega and theta / 大$\Omega$ 和大$\Theta$

> update：2017/12/11

前面两节课已经讲了$O()$表示法的形式化定义与基础示例证明，这节课视频再介绍三个相关的表示法。

1. $\Omega()$表示法：关注的对象是运行时间的下界，形式化定义是如果存在常数$c，n_0$使得对于任意$n\geq n_0$都有$T(n)\geq c\cdot f(n)$，则$T(n)=\Omega(f(n))$。示意图如下图所示。

   ![](http://7xwggp.com1.z0.glb.clouddn.com/bigoh_omega.png)

2. $\Theta()$表示法：关注的对象是同时满足$O()$和$\Omega()$，形式化定义是存在常数$c_1，c_2，n_0$使得对于任意$n\geq n_0$都有$c_1 \cdot f(n)\leq T(n)\leq c_2\cdot f(n)$。

3. $o()$表示法：和$O()$有所区别，形式化定义是对于所有的常数$c$，存在常数$n_0$使得对于任意$n\geq n_0$都有$T(n)\leq c\cdot f(n)$。

大多数情况下，还算关注$O()$表示法，因为设计者最关心的还是运行时间的上界。

最后需要说明的是，这些表示法并不是算法设计者或者计算机科学家发明的，早在19世纪就出现了，但是作为描述增长速率的标准语言，却是D.E. Knuth在1976年提出的$O，\Omega，\Theta$。

