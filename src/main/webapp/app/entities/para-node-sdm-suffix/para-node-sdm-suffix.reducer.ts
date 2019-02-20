import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IParaNodeSdmSuffix, defaultValue } from 'app/shared/model/para-node-sdm-suffix.model';

export const ACTION_TYPES = {
  SEARCH_PARANODES: 'paraNode/SEARCH_PARANODES',
  FETCH_PARANODE_LIST: 'paraNode/FETCH_PARANODE_LIST',
  FETCH_PARANODE: 'paraNode/FETCH_PARANODE',
  CREATE_PARANODE: 'paraNode/CREATE_PARANODE',
  UPDATE_PARANODE: 'paraNode/UPDATE_PARANODE',
  DELETE_PARANODE: 'paraNode/DELETE_PARANODE',
  SET_BLOB: 'paraNode/SET_BLOB',
  RESET: 'paraNode/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IParaNodeSdmSuffix>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ParaNodeSdmSuffixState = Readonly<typeof initialState>;

// Reducer

export default (state: ParaNodeSdmSuffixState = initialState, action): ParaNodeSdmSuffixState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PARANODES):
    case REQUEST(ACTION_TYPES.FETCH_PARANODE_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PARANODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PARANODE):
    case REQUEST(ACTION_TYPES.UPDATE_PARANODE):
    case REQUEST(ACTION_TYPES.DELETE_PARANODE):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PARANODES):
    case FAILURE(ACTION_TYPES.FETCH_PARANODE_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PARANODE):
    case FAILURE(ACTION_TYPES.CREATE_PARANODE):
    case FAILURE(ACTION_TYPES.UPDATE_PARANODE):
    case FAILURE(ACTION_TYPES.DELETE_PARANODE):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PARANODES):
    case SUCCESS(ACTION_TYPES.FETCH_PARANODE_LIST):
      const links = parseHeaderForLinks(action.payload.headers.link);
      return {
        ...state,
        links,
        loading: false,
        totalItems: action.payload.headers['x-total-count'],
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links)
      };
    case SUCCESS(ACTION_TYPES.FETCH_PARANODE):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PARANODE):
    case SUCCESS(ACTION_TYPES.UPDATE_PARANODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PARANODE):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.SET_BLOB:
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType
        }
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/para-nodes';
const apiSearchUrl = 'api/_search/para-nodes';

// Actions

export const getSearchEntities: ICrudSearchAction<IParaNodeSdmSuffix> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PARANODES,
  payload: axios.get<IParaNodeSdmSuffix>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IParaNodeSdmSuffix> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_PARANODE_LIST,
    payload: axios.get<IParaNodeSdmSuffix>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IParaNodeSdmSuffix> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PARANODE,
    payload: axios.get<IParaNodeSdmSuffix>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IParaNodeSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PARANODE,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IParaNodeSdmSuffix> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PARANODE,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IParaNodeSdmSuffix> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PARANODE,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType
  }
});

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
