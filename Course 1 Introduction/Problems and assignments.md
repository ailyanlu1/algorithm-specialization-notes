## Problems set 

1. 3-way-Merge Sort : Suppose that instead of dividing in half at each step of Merge Sort, you divide into thirds, sort each third, and finally combine all of them using a three-way merge subroutine. What is the overall asymptotic running time of this algorithm? (Hint: Note that the merge step can still be implemented in O(n) time.) 

   - [x] $n log (n)$

   - [ ] $n(log(n))^2$

   - [ ] $n^2log(n)$

   - [ ] n


   这道题是问如果Merge sort每次是把一个数组划分成三个部分，那么运行时间复杂度是多少。尽管是每个大问题划分成三个小问题，但是反映到recursion tree上，树的深度依旧是logarithmic，且每一层的merge操作的运行时间依旧是线性的，所以最终的复杂度仍然是$O(nlog(n))$。

2. You are given functions f and g such that $f(n)=O(g(n))$. Is $f(n)\cdot log_2(f(n)^c)=O(g(n)\cdot log_2(g(n))) $? (Here c is some positive constant.) You should assume that f and g are nondecreasing and always bigger than 1.

   - [x] True
   - [ ] False
   - [ ] Sometimes yes, sometimes no, depending on the constant $c$
   - [ ] Sometimes yes, sometimes no, depending on the function $f$ and $g$

   这道题是问如果函数$f$与$g$满足$f(n)=O(g(n))$，那么$f(n)\cdot log_2(f(n)^c)=O(g(n)\cdot log_2(g(n))) $是否成立。根据条件可知，存在$c_1，n_0$使得当$n\geq n_0$时有$f(n)\leq c_1\cdot g(n)$。假设所问结论成立，则存在$c_2，n_0$使得当$n\geq n_0$时有$f(n)\cdot log_2(f(n)^c)=O(g(n)\cdot log_2(g(n)))$，即$f(n)\cdot log_2(f(n)^c)=c_2\cdot g(n)\cdot log_2(g(n))$，化简规约一下，$c\cdot f(n)\cdot log_2(f(n))=c_2\cdot g(n)\cdot log_2(g(n))$，将$f(n)\leq c_1\cdot g(n)$代入，显然是可以成立的

3. Assume again two (positive) nondecreasing functions f and gsuch that $f(n)=O(g(n))$. Is $2^{f(n)}=O(2^{g(n)})$ ? (Multiple answers may be correct, you should check all of those that apply.)

   - [ ] Always
   - [ ] Never
   - [x] Sometimes yes, sometimes no (depending on $f$ and $g$)
   - [x] Yes if $f(n)\leq g(n)$ for all sufficiently large $n$

   这道题是问如果函数$f$与$g$满足$f(n)=O(g(n))$，那么 $2^{f(n)}=O(2^{g(n)})$ 是否成立。我们可以通过一些特殊的例子来解答。

   - 假设$f(n)=g(n)=n$，这种情况下，$2^n=O(2^n)$是显然成立的
   - 假设$f(n)=10n，g(n)=n$，这种情况下，显然有$2^{10n}\neq O(2^n)$，这个在Course2课程的案例中介绍过证明

   综上所述，这个结论不总是对的，也不总是错的。

4. k-way-Merge Sort. Suppose you are given $k$ sorted arrays, each with $n$ elements, and you want to combine them into a single array of $kn$ elements. Consider the following approach. Using the merge subroutine taught in lecture, you merge the first 2 arrays, then merge the $3^{rd}$ given array with this merged version of the first two arrays, then merge the  $4^{th}$  given array with the merged version of the first three arrays, and so on until you merge in the final ($k^{th}$) input array. What is the running time taken by this successive merging algorithm, as a function of $k$ and $n$? (Optional: can you think of a faster way to do the k-way merge procedure ?)

   - [ ] $\Theta(nlog(k))$
   - [x] $\Theta(nk^2)$
   - [ ] $\Theta(n^2k)$
   - [ ] $\Theta(nk)$

   这道题是问在k-way归并排序中，如果给定k个有序数组，每个数组有n个元素，现在要将它们合并成一个包含$kn$个元素的大数组，归并操作如下：首先归并前两个数组，得到的新数组继续和第三个数组合并，依此类推。问这个连续的归并操作的运行时间复杂度是多少。每次归并操作都是线性时间$O(kn)$的，且有k次归并，因此上界的时间复杂度是$O(k^2n)$。对于下界，每次至少有$\frac{1}{2}k$个元素参与合并，因此下界运行时间是$\Omega(nk^2)$。综上所述，运行时间满足$\Theta(nk^2)$

5. Arrange the following functions in increasing order of growth rate (with g(n) following $f(n)$ in your list if and only if $f(n)=O(g(n)))$.

   a) $\sqrt n$

   b)10n

   c) $n^{1.5}$

   d)$2^{\sqrt log(n)}$

   e)$n^{\frac{5}{3}}$

   这道题是要求把以下五个函数按照增长率由小到大排序。答案是$2^{\sqrt log(n)} \leq \sqrt n \leq n^{1.5} \leq n^{\frac{5}{3}} \leq 10^n$。前面两个的证明可以是左右两边同时去对数。后面几个显然成立。

## Programming assignment

In this programming assignment you will implement one or more of the integer multiplication algorithms described in lecture.

To get the most out of this assignment, your program should restrict itself to multiplying only pairs of single-digit numbers. You can implement the grade-school algorithm if you want, but to get the most out of the assignment you'll want to implement recursive integer multiplication and/or Karatsuba's algorithm.

So: what's the product of the following two 64-digit numbers?

3141592653589793238462643383279502884197169399375105820974944592

2718281828459045235360287471352662497757247093699959574966967627

这道题是要求利用前面课程所讲的分治法`Karatsuba整数求乘积`来求解，具体的代码如下：

```java
public class Karatsuba {
    public static BigInteger karatsuba(BigInteger x, BigInteger y) {
        int N = Math.max(x.bitLength(), y.bitLength());
        if (N <= 2000) return x.multiply(y);
        N = (N / 2) + (N % 2);

        BigInteger b = x.shiftRight(N);
        BigInteger a = x.subtract(b.shiftLeft(N));
        BigInteger d = y.shiftRight(N);
        BigInteger c = y.subtract(d.shiftLeft(N));

        BigInteger ac    = karatsuba(a, c);
        BigInteger bd    = karatsuba(b, d);
        BigInteger abcd  = karatsuba(a.add(b), c.add(d));

        return ac.add(abcd.subtract(ac).subtract(bd).shiftLeft(N)).add(bd.shiftLeft(2*N));
    }


    public static void main(String[] args) {
        BigInteger a = new BigInteger("3141592653589793238462643383279502884197169399375105820974944592");
        BigInteger b = new BigInteger("2718281828459045235360287471352662497757247093699959574966967627");
        BigInteger c = karatsuba(a, b);
        System.out.println(c.toString());
    }
}
```
