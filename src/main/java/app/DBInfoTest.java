package app;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;

public class DBInfoTest
{
    private static HashMap<String,String> map;

    public DBInfoTest()
    {
        if(map==null)
        {
            try
            {
                BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/dbinfo_test.cfg")));
                String line;
                map = new HashMap<>();

                while ((line = reader.readLine()) != null)
                {
                    String[] data = line.split("=");
                    map.put(data[0],data[1]);
                }

            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    public String get(String key)
    {
        return map.get(key);
    }
}
