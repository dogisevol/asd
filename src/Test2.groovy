import java.math.MathContext
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat


for (def i = 100; i >= 0; --i )
	printf("%d\n", i)

def quickSort
def partition

quickSort = { arr, low, high->
	if(low < high){
		def p = partition(arr, low, high)
		quickSort(arr, low, p-1)
		quickSort(arr, p+1, high)
	}
}

partition = {arr, low, high->
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

def list = [1, 0, 3, 5, 2, 9, 7, 6]
quickSort(list, 0, list.size()-1)
println list