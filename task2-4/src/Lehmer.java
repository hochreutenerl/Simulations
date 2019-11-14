public class Lehmer implements Analyzable {

    private int m = 100000001;
    private int a = 23;
    private int c = 0;
    private int x;

    public Lehmer(int seed) {
        x = seed;
    }

    public int nextValue() {
        x = (a * x + c) % m;
        return x;
    }
}

/*
    real(dp),parameter :: m=2._dp**31-1._dp
        m_1 = 1._dp/m
        a = 16807._dp
        real(wp) function random()
        seed=modulo(seed*a,m)
        random=seed*m_1
        return
        end function random

 */