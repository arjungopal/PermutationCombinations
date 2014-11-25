import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/* Author arjun gopal net id :  axg145630 . Implementation of Algorithm L by Dr. Donald Knuth with some small alteration to support the permutation of r items from n.
 * Performance of the algorithm is adaptive and dependent on the r value in nPr. Explanations are provided as comments along with the methods.*/
public class AXG145630Problem1 {
	static long count = 0;

	public static void main(String[] args) throws IOException {

		count = 0;
		// Reading the input values
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int k = sc.nextInt();
		int v = sc.nextInt();
		long start_time = 0;
		long end_time = 0;
		if (k > n) {
			System.out.println("Invalid Input!");
			System.exit(0);
		}
		if (v == 0 || v == 2) {
			start_time = System.currentTimeMillis();
			getPermutations(n, k, v);
			end_time = System.currentTimeMillis();
		} else if (v == 1 || v == 3) {
			start_time = System.currentTimeMillis();
			getCombinations(n, k, v);
			end_time = System.currentTimeMillis();
		} else
			System.out.println("Invalid Input!");
		System.out.println(count + " " + (end_time - start_time));

	}

	private static void getCombinations(int n, int k, int v) {
		if (k == 0) {
			count++;
			return;
		}
		int[] arr = new int[n];
		for (int i = 0; i < n; i++)
			arr[i] = i + 1;

		int[] aux = new int[k];
		generateCombinations(arr, aux, 0, n, 0, k - 1, v);
	}

	/*
	 * @param arr : the main array which is initialized with n elements
	 * 
	 * @param aux : auxilary array in which required combinations is calculated.
	 * 
	 * @param begin : Initialized to 0 and incremented by 1 in each recursion
	 * 
	 * @param n : size of the main array.
	 * 
	 * @param r : initilized to 0 and incremented by in each recursion
	 * 
	 * @param k : k elements which needs to be selected from n items
	 * 
	 * @param v : verbose condition
	 */

	static void generateCombinations(int arr[], int aux[], int begin, int n,
			int r, int k, int v) {
		// Base case
		if (k < 0)
			return;

		if (r == k) {
			// Copy all the remaining items to the last location in each
			// iteration and print
			for (int i = begin; i < n; i++) {
				aux[r] = arr[i];
				if (v == 3) {
					visit(aux, k);
				}
				count++;

			}

		} else if (r < k) {
			for (int i = begin; i < n - (k - r); i++) {
				aux[r] = arr[i];
				// call generateCombinations with begin as i+1 and r as r+1;
				generateCombinations(arr, aux, i + 1, n, r + 1, k, v);
			}
		}

		else
			return;
	}

	private static void visit(int[] aux, int k) {
		for (int j = 0; j <= k; j++) {
			System.out.print(aux[j] + " ");

		}
		System.out.println();
	}

	// The generatePermutation is implementation of Algorithm L from Knuth's Art
	// of Programming. The algorithm is altered little to get the nPr in
	// lexicographic order. First the array is rotated/reversed from the index
	// location of r. for example if array is {1,2,3,4,5} and r=2 (5p2) then as
	// a first step the array is changed to {1,2,5,4,3}. Now first step of
	// Algorithm L will give the index position ( call it index) as 1 which is
	// having value 2.
	// The next pointer is started from the value right to index which will give
	// the next largest value in lexicographic order. which is 3 here. It will
	// get replaced with 2. So array becomes {1,3,5,4,2}. This is continued till
	// we get an index value less than r. the array will get modified like
	// {1,4,5,3,2} , {1,5,4,3,2},{2,5,4,3,1}. At this point Array
	// will be rotated from the index position which gives {2,1,3,4,5} and then
	// the process is repeated till index becomes less than 0.

	/*
	 * @param n : total number of items
	 * 
	 * @param k : no of items to be permuted
	 * 
	 * @param v : verbose input
	 */
	private static void getPermutations(int n, int k, int v) {
		/*
		 * n is the total no of elements and k is the no of elements that has to
		 * be picked up in each permutations. v decides whether the permutations
		 * has to be printed or not
		 */
		if (k == 0) {
			count++;
			return;
		}
		// result array to store the permutations of our interest
		int[] result;
		int[] arr;
		arr = new int[n];
		// arr is the main array. putting elements 1..n in this array
		for (int i = 0; i < n; i++)
			arr[i] = i + 1;

		// Rotate the array from the k index.. This is for getting the next item
		// in lexicographic order in each
		// permutations.
		rotate(arr, n, k - 1);
		// Visit the permutation for the first time
		result = new int[k];
		visit(arr, v, result, k);

		while (true) {

			// Find the right most element i such that arr[i]<arr[i+1]
			int ind = findIndexPosition(arr, n);
			if (ind < 0) {
				// We are done here.. Can stop the iteration
				break;
			}

			int rightIndex = ind + 1;
			int j = ind + 2;
			// This loop will find the next largest element that we need to
			// replace at the index position.
			while (j < n) {
				if ((arr[j] > arr[ind]) && (arr[j] < arr[rightIndex]))
					rightIndex = j;
				j++;
			}
			// swap the index value and next largest element which we have found
			// in the above loop.
			exchange(arr, ind, rightIndex);
			if (k - 1 > ind) {
				// We are done with one round . Now rotate the array from the
				// ind location.
				// we have to rotate it once again from the k index to restart
				// the loop again.
				rotate(arr, n, ind);
				rotate(arr, n, k - 1);
			}

			visit(arr, v, result, k);
		}
	}

	// Visit method takes the array and copy the result to the result array
	// which of the size k. Then print the
	// Result array depending on the verbose input
	/*
	 * @param arr : main array
	 * 
	 * @param result : array of size k, which conatins the next
	 * permutation/combination
	 * 
	 * @param v : verbose input
	 * 
	 * @param k : no of items to be permuted
	 */
	private static void visit(int[] arr, int v, int[] result, int k) {
		if (v == 2) {
			for (int i = 0; i < k; i++)
				result[i] = arr[i];
			for (int j = 0; j < result.length; j++)
				System.out.print(result[j] + " ");
			System.out.println();
		}
		count++;

	}

	/*
	 * this method takes an array as input and reverse the array from the begin
	 * index(n) to end index(i)
	 * 
	 * @param arr: input array
	 * 
	 * @param n : begin index
	 * 
	 * @param i : end index
	 */
	private static void rotate(int[] arr, int n, int i) {
		int start = i + 1;
		int end = n - 1;
		while (start < end) {
			exchange(arr, start, end);
			start++;
			end--;
		}
	}

	/*
	 * This method exchanges the values at two given indexes in the array
	 * 
	 * @param a : array in which swapping has to be done
	 * 
	 * @param i : index 1
	 * 
	 * @pram j : index 2
	 */
	private static void exchange(int[] a, int i, int j) {
		int temp = a[i];
		a[i] = a[j];
		a[j] = temp;
	}

	/*
	 * this method does a scanning from right to left and find the first index i
	 * such that arr[i]<arr[i+1]
	 * 
	 * @param arr : main array
	 * 
	 * @param n : size of array
	 */
	private static int findIndexPosition(int[] arr, int n) {
		for (int i = n - 2; i >= 0; i--)
			if (arr[i] < arr[i + 1])
				return i;
		return -1;
	}

}
