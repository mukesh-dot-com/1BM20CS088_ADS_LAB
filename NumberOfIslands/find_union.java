
class DisjointSet {
  int[] parent, rank;
  
  public DisjointSet(int n) {
    parent = new int[n];
    rank = new int[n];
    for (int i = 0; i < n; i++) {
      parent[i] = i;
    }
  }
  
  int find(int x) {
    if (parent[x] != x) {
      parent[x] = find(parent[x]);
    }
    return parent[x];
  }
  
  void union(int x, int y) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX != rootY) {
      if (rank[rootX] > rank[rootY]) {
        parent[rootY] = rootX;
      } else {
        parent[rootX] = rootY;
        if (rank[rootX] == rank[rootY]) {
          rank[rootY]++;
        }
      }
    }
  }
}

public class FindUnion {
  public static void main(String[] args) {
    int[][] matrix = new int[][] {
      {1, 1, 0, 0, 0},
      {0, 1, 0, 0, 1},
      {1, 0, 0, 1, 1},
      {0, 0, 0, 0, 0},
      {1, 0, 1, 0, 1}
    };
    int m = matrix.length;
    int n = matrix[0].length;
    DisjointSet ds = new DisjointSet(m * n);
    int count = 0;
    int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    for (int i = 0; i < m; i++) {
      for (int j = 0; j < n; j++) {
        if (matrix[i][j] == 1) {
          count++;
          for (int k = 0; k < 8; k++) {
            int x = i + dx[k];
            int y = j + dy[k];
            if (x >= 0 && x < m && y >= 0 && y < n && matrix[x][y] == 1) {
              int root1 = ds.find(i * n + j);
              int root2 = ds.find(x * n + y);
              if (root1 != root2) {
                count--;
                ds.union(root1, root2);
              }
            }
          }
        }
      }
    }
    for(int i = 0;i<matrix.length;i++){
        System.out.println();
        for(int j =0;j<matrix[0].length;j++){
            System.out.print(matrix[i][j]+" ");
        }
    }
    System.out.println("\n\nNumber of islands: " + count);
  }
}

