import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat


class Test {

	static main(args) {
		def x = 5
		def y = 2
		println Integer.toBinaryString(x)
		println Integer.toBinaryString(y)
		x = x & y
		println Integer.toBinaryString(x)
		println x
		println Integer.toBinaryString(y)
		println y


		println Integer.toBinaryString((5 & (1<< 1)))


		def arr = [0, 9, 8, 7, 6, 5, 4, 3, 2, 1]
		quickSort(arr, 0, 9)
		arr = [2, -1, -4, 10, -1, 2]
		print findMaxSubarray(arr, 0, arr.size())
		print maxLinearSubarray(arr)

		def deldir
		deldir = { File file ->
			if (file.directory) {
				file.eachFile { deldir(it) }
			}
			return file.delete()
		}
		println deldir(new File("C:/1/3/"))


		def stack = new Stack()
		stack.push(8)
		stack.push(8)
		stack.push(8)
		stack.push(8)
		stack.push(8)

		def tempStack = new Stack()

		def depth = Integer.MAX_VALUE
		def count = [0]

		while(depth > 0){
			depth = sortStack(stack, tempStack, depth, count)
		}

		println stack
		println count




		def replaceSpaces = { char[] input, int trueLength ->
			def spaces = 0
			for(def i=0; i < trueLength; i++){
				if(input[i] == ' ')
					spaces++
			}
			int end = trueLength + (spaces * 2) - 1;
			for (int textEnd = trueLength - 1; textEnd < end; textEnd--) {
				println input
				if (input[textEnd] == ' ') {
					input[end--] = '0';
					input[end--] = '2';
					input[end--] = '%';
				} else {
					input[end--] = input[textEnd];
				}
			}
			return input
		}

		println replaceSpaces("Mr John Smith             ".toCharArray(), 13)
	}


	public static int sortStack (Stack stack, Stack tempStack, int depth, count){
		if(stack.isEmpty())
			return 0
		def item = stack.pop()
		def max = item
		def i = 0
		while(!stack.isEmpty() && i < depth){
			count[0]++
			item = stack.pop()
			i++
			if(max < item){
				tempStack.push(max)
				max = item
			}else{
				tempStack.push(item)
			}
		}

		stack.push(max)

		while(!tempStack.isEmpty()){
			stack.push(tempStack.pop())
		}
		return --i
	}

	public static maxLinearSubarray(arr){
		def maxEndingHere = arr[0]
		def maxSoFar = arr[0]
		for (def i = 1; i < arr.size(); i++){
			maxEndingHere = 0> maxEndingHere + arr[i]? 0: maxEndingHere + arr[i]
			maxSoFar = maxSoFar> maxEndingHere?maxSoFar:maxEndingHere
		}
		return maxSoFar
	}


	public static findMaxSubarray(arr, low, high){
		if(low == high){
			return ["left": low, "right": high, "sum":arr[low]]
		}else{
			int mid = (high+low)/2
			def left = findMaxSubarray(arr, low, mid)
			def right = findMaxSubarray(arr, mid+1, high)
			def crossing = findMaxCrossingSubarray(arr, low, mid, high)
			if(left["sum"] >= right["sum"] && left["sum"] >= crossing["sum"])
				return left
			if(right["sum"] >= left["sum"] && right["sum"] >= crossing["sum"])
				return right
			return crossing
		}
	}


	public static findMaxCrossingSubarray(arr, low, mid, high){
		def sum = 0
		def maxLeft = low
		def leftSum = Integer.MIN_VALUE
		for(def i = mid; i > low; i--){
			sum += arr[i]
			if(sum > leftSum){
				leftSum = sum
				maxLeft = i
			}
		}
		sum = 0
		def maxRight = high
		def rightSum = Integer.MIN_VALUE
		for(def i = mid+1; i < high; i++){
			sum += arr[i]
			if(sum > rightSum){
				rightSum = sum
				maxRight = i
			}
		}
		return ["left": maxLeft, "right": maxRight, "sum": (leftSum==Integer.MIN_VALUE || rightSum==Integer.MIN_VALUE)?Integer.MIN_VALUE:rightSum + leftSum]
	}


	public static quickSort(arr, low, high){
		if(low < high){
			def m = partition(arr, low, high)
			quickSort(arr, low, m - 1)
			quickSort(arr, m + 1, high)
		}
	}

	public static partition(arr, low, high){
		def pivot = arr[high]
		def i = low
		def t
		for(def j = low; j < high; j++){
			if(arr[j] <= pivot){
				t = arr[j]
				arr[j]  = arr[i]
				arr[i]  = t
				i++
			}
		}
		t = arr[i]
		arr[i]  = arr[high]
		arr[high]  = t

		return i
	}
}
