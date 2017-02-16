import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat


class Test1 {

	static main(args) {
		def arr = [0, 9, 4, 7, 6]
		quickSort(arr, 0, arr.size() -1)
		println arr
		arr = [0, 9, 4, 7, 6]
		println insertionSort(arr)
		arr = [4, 5, 1, 8, 6]
		println bubbleSort(arr)
	}

	public static bubbleSort(arr){
		def temp
		for(def i=0; i<arr.size(); i++){
			for(def j=i; j < arr.size(); j++){
				if(arr[i] > arr[j]){
					temp = arr[j]
					arr[j] = arr[i]
					arr[i] = temp
				}
			}
		}
		return arr
	}

	public static quickSort(arr, low, high){
		if(low < high){
			def p = partition(arr, low, high)
			quickSort(arr, low, p-1)
			quickSort(arr, p+1, high)
		}
	}


	public static insertionSort(arr){
		for(def i =1; i < arr.size(); i++){
			def j = i
			while(j > 0 && arr[j]<arr[j-1] ){
				def temp
				temp = arr[j]
				arr[j] = arr[j-1]
				arr[j-1] = temp
				j--
			}
		}
		return arr
	}

	public static partition(arr, low, high){
		def p = arr[high]
		def i = low
		def temp
		for(def j = low; j < high; j++){
			if(arr[j] <= p){
				temp = arr[j]
				arr[j] = arr[i]
				arr[i] = temp
				i++
			}
		}
		temp = arr[i]
		arr[i] = arr[high]
		arr[high] = temp

		return i
	}
}
