import java.util.Arrays;
import java.util.Scanner;

/*Author Arjun Gopal Net Id : axg145630 */
// Problem solved using the Algorithm L explained by Dr.Donald Knuth in Art of Computer Programming. 
public class AXG145630Problem2 {
	static long count = 0;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int v = sc.nextInt();
		int[] arr = new int[n];

		for (int i = 0; i < n; i++) {
			arr[i] = sc.nextInt();
		}
		long start_time = 0;
		long end_time = 0;
		if (v == 0 || v == 1) {
			start_time = System.currentTimeMillis();
			getPermutations(arr, n, v);
			end_time = System.currentTimeMillis();
		} else {
			System.out.println("Invalid Input");
			System.exit(0);
		}
		System.out.println(count + " " + (end_time - start_time));

	}

	/*
	 * Implementation of Algorithm L
	 * 
	 * @param arr : main array
	 * 
	 * @param n : size of the array
	 * 
	 * @param v : verbose input
	 */
	private static void getPermutations(int[] arr, int n, int v) {
		boolean flag = false;

		// Double Pivot Quick sort used by Java.
		Arrays.sort(arr);
		if (arr.length == 0 || arr.length == 1)
			return;
		if (v == 1)
			visit(arr);
		count++;
		while (true) {
			int j = arr.length - 2;
			// Find the index element by scanning from right to left
			while (arr[j] >= arr[j + 1]) {
				if (j == 0) {
					flag = true;
					break;

				}
				j--;
			}
			// We are done.
			if (flag)
				break;
			int m = arr.length - 1;
			// Find the next element which will be swapped with the index
			while (arr[j] >= arr[m]) {
				m--;
			}
			exchange(arr, j, m);

			int k = j + 1;
			m = arr.length - 1;
			// Reverse the array from the location next to the calculated index
			while (k < m) {
				exchange(arr, k, m);
				k++;
				m--;
			}
			// Visit the array
			if (v == 1)
				visit(arr);
			count++;
		}

	}

	/*
	 * @param arr : main array Prints the array
	 */
	public static void visit(int[] arr) {
		for (int i = 0; i < arr.length; i++)
			System.out.print(arr[i] + " ");
		System.out.println();
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
	private static void exchange(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
