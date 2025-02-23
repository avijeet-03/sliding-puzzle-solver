import React, {useEffect} from "react";

const Popup = ({message, onClose}) => {
    useEffect(() => {
        const timer = setTimeout(onClose, 5000);
        return () => clearTimeout(timer);
    }, [onClose]);

    return (
        <div className="fixed top-5 right-5 bg-red-600 text-white p-4 rounded-lg shadow-lg border-2 border-red-800 w-80">
            <p className="font-bold">⚠️ Error</p>
            <p>{message}</p>
        </div>
    );

};

export default Popup;