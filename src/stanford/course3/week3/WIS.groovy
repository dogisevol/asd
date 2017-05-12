package stanford.course3.week3

Scanner scanner = new Scanner(new File("mwis.txt"))
def c = 0;
def array = new int[scanner.nextInt()]
while (scanner.hasNext()) {
    array[c++] = scanner.nextInt()

}


i = array.length - 1
byte result = 0
def set = []
while (i >= 1) {
    if (array[i] == array[i - 1])
        i--
    else {
        switch (i+1) {
            case 1:
                result = result | (1 << 1);
                break
            case 2:
                result = result | (1 << 2);
                break
            case 3:
                result = result | (1 << 3);
                break
            case 4:
                result = result | (1 << 4);
                break
            case 17:
                result = result | (1 << 5);
                break
            case 117:
                result = result | (1 << 6);
                break
            case 517:
                result = result | (1 << 7);
                break
            case 997:
                result = result | (1 << 8);
                break
        }
        set.add(array[i])

        i -= 2
    }
}
println(set)
String s2 = String.format("%8s", Integer.toBinaryString(result & 0xFF)).replace(' ', '0');
println(s2)
