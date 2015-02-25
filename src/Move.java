
public class Move 
{
	int[][] mDirections;
	int mX, mY;
	int mCount;
	double mRank = 0;
	
	public Move(int[][] directions, int x, int y)
	{
		mDirections = directions;
		mX = x;
		mY = y;
	}
	
	public int[][] getDirections()
	{
		return mDirections;
	}
	
	public boolean isMove()
	{
		for(int x = 0; x < mDirections.length; x++)
			if(mDirections[x][0] == 1)
				return true;
		
		return false;
	}
	
	public boolean hasDirection(int direction)
	{
		if(mDirections[direction][0] == 1)
			return true;
		
		return false;
	}
	
	public void setCount(int count)
	{
		mCount = count;
	}
	
	public int getCount()
	{
		return mCount;
	}
	
	public void addRank(int addition)
	{
		mRank += addition;
	}
	
	public double getRank()
	{
		return mRank;
	}
}
