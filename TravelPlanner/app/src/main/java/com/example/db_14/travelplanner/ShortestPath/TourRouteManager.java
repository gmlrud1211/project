package com.example.db_14.travelplanner.ShortestPath;

import com.skp.Tmap.TMapData;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapPolyLine;

import java.util.ArrayList;
//http://esckey.tistory.com/81 참고
/**
 * Created by db-14 on 2017. 2. 14..
 */
public class TourRouteManager {

    ArrayList<TMapPoint> base_path, opt_path;
    int n;
    boolean visit[];
    double data[][];
    int s, e;

    public TourRouteManager(ArrayList<TMapPoint> base_path) {
        this.base_path = base_path;
        n = base_path.size();
        visit = new boolean[n];
        data = new double[n][n];
        opt_path = new ArrayList<TMapPoint>();

        for(int i=0; i<n; i++)
        {
            visit[i] = false;
        }

    }

    public void setOptimalRoute() throws Exception
    {
        if(n<3)
        {
            opt_path = base_path;
            return;
        }

        setArrayDistance(base_path);
        findShortestPath();
    }

    private void setArrayDistance(ArrayList<TMapPoint> list) throws Exception {
        TMapData tMapData = new TMapData();
        TMapPolyLine path;

        for(int i=0; i<n; i++)
        {
            for(int j=i; j<n; j++)
            {
                path = tMapData.findPathData(list.get(i), list.get(j));
                data[i][j] = data[j][i] = path.getDistance();
            }
        }
    }

    private void findShortestPath() {
        s = 0; e = n-1;

        int k = 0;
        double min = 1000000000;
        boolean find = false;

        visit[0] = true;
        opt_path.add(base_path.get(0));

        for(int i=s; i<e; i=k)
        {
            for(int j=0; j<e;j++)
            {
                if ( data[i][j]!=0 && data[i][j]<min && !visit[j] )
                {
                    min = data[i][j];
                    k = j;
                    find = true;
                }
            }

            if(!find)
            {
                visit[e] = true;
                opt_path.add(base_path.get(e));
                break;
            }

            visit[k] = true;
            opt_path.add(base_path.get(k));

            min = 1000000000;
            find = false;
        }

    }

    public ArrayList<TMapPoint> getOptimalRoute() throws Exception {

        return opt_path;
    }

}
