package api_client;

public enum ModelAction
{
    ADD{
        @Override
        public String toString()
        {
            return "add";
        }
    },
    EDIT{
        @Override
        public String toString()
        {
            return "edit";
        }
    },
    DELETE{
        @Override
        public String toString()
        {
            return "delete";
        }
    }

}
