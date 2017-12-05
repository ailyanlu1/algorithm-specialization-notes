title: Algorithm专项课程—分治、排序与搜索、随机算法
description: Coursera上学习“Algorithm专项课程”的第一课程，主要涉及渐近表示法(大O表示法)，排序与搜索算法，分治算法(主项定理、整数与矩阵乘法、最近点对问题)，以及随机算法(快速排序、最小割集收缩算法)。
date: 2017-12-02 
banner:
toc: false
category: Tech
tag: [Algorithm, Coursera]

---

# Introduction / 简介

求解：两个n位整数相乘

输入：两个n位的整数x与y

输出：两者的乘积

解决方案：

1. Naive方法：确定基本运算，即y的每一位都要与x中的n位相乘，就是n次基本运算了，乘积十位上的数字还要进上去，这就涉及一些额外的加法运算，但是总之在任何情况下，基本运算的总量最多是2n。同理，得到每一部分乘积最多都需要2n步运算，而部分乘积的总次数为n，即要做$2n^2$次基本运算。然而还没有结束，还要把这些部分乘积累加起来，才能得到最终结果，这一步的求和运算量最多也是$2n^2$次，因此计算两个n位整数的乘积的基本运算操作量为$4n^2$，是输入长度n的二次函数。

2. Karatsuba方法：利用分治的思想，把x表示为ab，y表示成cd，即a=56，b=78，c=12，d=34。通过以下前三个递归步骤，利用$x\cdot y = 10000\cdot a\cdot c + 100\cdot a\cdot d + 100\cdot b\cdot c + b\cdot d$这一思想来求得最终的结果。可以发现，第三步化简之后就是ad+bc，但是在具体操作时不是分别计算ad和bc的乘积再求和，而是使用化简之前的表示，先各自求得a与b之和、c与d之和，然后求积，最后减去已知的ac与bd。这样是为了减少一次递归次数，毕竟加减法才是最基本的运算。

   形式化的方式来归纳以下，就是：

   - $x = 10^{\frac{1}{2}}\cdot a + b$，$y= 10^{\frac{1}{2}}\cdot c + d$
   - $x\cdot y=10^{n}a\cdot c + 10^{\frac{1}{2}}(a\cdot d+ b\cdot c) + b\cdot d$，$n=4$ ($\star$)
   - 递归计算ac，ad，bc，bd，然后代入上面的星式求得最终乘积
   - 为了减少一次递归运算，不需要单独计算ad与bc的乘积

   ![](http://7xwggp.com1.z0.glb.clouddn.com/karatsuba.jpg)





> 对于一个优秀的算法设计者而言，最重要的原则就是拒绝满足。
>
> Perhaps the most important principle for the good algorithm designer is to refuse to be content.
>
> The algorithm design space is surprisingly rich!
>
> 算法的设计空间，比我们想象中的要广阔得多！

讲解模式：

1. 确定输入与输出
2. 给出解决方案，即算法，使得输入转化为输出

# Big-oh notation / 大O表示法

# Sorting and searching / 排序与检索

# Divide and conquer / 分治

# Randomized algorithms / 随机算法







