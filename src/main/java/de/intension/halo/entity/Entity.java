package de.intension.halo.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Top level data structure of a HALO document.
 * Contains the ressource itself and describes messages and available actions.
 */
@Data
@Accessors(chain = true)
public final class Entity
{

    /**
     * The data located at the requested ressource.
     * 
     * @param data Set data of the ressource.
     * @return Data of the ressource.
     */
    private Object        data;
    /**
     * List of messages that inform about success or failure of a request.
     * <br/>
     * <br/>
     * Should contain at least one element in the case of failure.
     * 
     * @param messages Set success or error messages.
     * @return Success or error messages.
     */
    private List<Message> messages;
    /**
     * List of available subressources under the requested ressource.
     * 
     * @param links Set subressources of the ressource.
     * @return Subressources of the ressource.
     */
    private List<Link>    links;

    /**
     * Add a new subressource to the list.
     * 
     * @param link Subressource to add to {@link #links}.
     */
    public Entity addLink(Link link)
    {
        if (links == null) {
            links = new ArrayList<>();
        }
        links.add(link);
        return this;
    }

    /**
     * Add a new message to the list.
     * 
     * @param message Message to add to {@link #messages}.
     */
    public Entity addMessage(Message message)
    {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(message);
        return this;
    }
}
