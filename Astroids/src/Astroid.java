import javax.swing.JFrame;




public class Astroid{
	
	public static final int WHIDTH = 450;
	public static final int HEIGHT = WHIDTH / 12 * 9;
	public static final int SCALE = 2;
	public static final String TITLE = "Astroid Game";
	
	public static void main(String[] args)
    {	
		
        AstroidGUI gui = new AstroidGUI();     
        JFrame frame = new JFrame(TITLE);
        frame.add(gui);       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WHIDTH * SCALE + 6, HEIGHT * SCALE + 28); 
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);   
        frame.setVisible(true);
        
    }
	
}
